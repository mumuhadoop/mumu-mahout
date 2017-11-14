package com.lovecws.mumu.mahout.recommender.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.mahout.math.VarLongWritable;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WikiDriver {

    //hadoop的安装目录
    static final String HADOOP_HOME = "/users/sunwangdong/hadoop/hadoop-2.5.2";
    //HDFS的临时目录，用来放job1的输出结果，注意这个地址是放到hdfs节点上的地址
    static final String TMP_PATH = "/sunwangdong/tmp/part-r-00000";

    //操作hdfs的文件夹
    static void OperatingFiles(String input, String output) {
        Path inputPath = new Path(input);
        Path outputPath = new Path(output);
        Path tmpPath = new Path(TMP_PATH);
        Configuration conf = new Configuration();
        conf.addResource(new Path(HADOOP_HOME + "/etc/hadoop/core-site.xml"));
        conf.addResource(new Path(HADOOP_HOME + "/etc/hadoop/hdfs-site.xml"));
        try {
            FileSystem hdfs = FileSystem.get(conf);
            if (!hdfs.exists(inputPath)) {
                System.out.println("input path no exist!");
            }
            if (hdfs.exists(outputPath)) {
                System.out.println("output path:" + outputPath.toString() + " exist, deleting this path...");
                hdfs.delete(outputPath, true);
            }
            if (hdfs.exists(tmpPath)) {
                System.out.println("tmp path:" + tmpPath.toString() + " exist, deleting this path...");
                hdfs.delete(tmpPath, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        if (args.length != 3) {
            System.out.println("useage: <input dir> <temp dir> <output dir>");
            System.exit(1);
        }
        //OperatingFiles(args[0], args[1]);
        String input = args[0];
        String tempOutput = args[1];
        String output = args[2];
        Configuration job1Conf = new Configuration();
        Job job1 = new Job(job1Conf, "job1");
        job1.setJarByClass(WikiDriver.class);
        job1.setMapperClass(WikipediaToItemPrefsMapper.class);
        job1.setReducerClass(WikipediaToUserVectorReducer.class);
        job1.setMapOutputKeyClass(VarLongWritable.class);
        job1.setMapOutputValueClass(VarLongWritable.class);

        //将job1输出的文件格式设置为SequenceFileOutputFormat
        job1.setInputFormatClass(TextInputFormat.class);
        job1.setOutputFormatClass(SequenceFileOutputFormat.class);
        job1.setOutputKeyClass(VarLongWritable.class);
        job1.setOutputValueClass(VectorWritable.class);
        FileInputFormat.addInputPath(job1, new Path(input));
        FileOutputFormat.setOutputPath(job1, new Path(tempOutput));
        job1.waitForCompletion(true);
        display(tempOutput, "SequenceFile");

        Configuration job2Conf = new Configuration();
        Job job2 = new Job(job2Conf, "job2");

        job2.setJarByClass(WikiDriver.class);
        job2.setMapperClass(UserVectorToCooccurrenceMapper.class);
        job2.setReducerClass(UserVectorToCooccurrenceReducer.class);
        job2.setMapOutputKeyClass(IntWritable.class);
        job2.setMapOutputValueClass(IntWritable.class);
        job2.setOutputKeyClass(IntWritable.class);
        job2.setOutputValueClass(VectorWritable.class);

        //将job2的输入文件格式设置为SequenceFileInputFormat
        job2.setInputFormatClass(SequenceFileInputFormat.class);
        job2.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job2, new Path(tempOutput));
        FileOutputFormat.setOutputPath(job2, new Path(output));
        job2.waitForCompletion(true);
        display(output, null);
    }

    public static void display(String path, String fileType) throws URISyntaxException, IOException {
        DistributedFileSystem distributedFileSystem = new DistributedFileSystem();
        Configuration configuration = new Configuration();
        distributedFileSystem.initialize(new URI("hdfs://192.168.11.25:9000"), configuration);
        FileStatus[] fileStatuses = distributedFileSystem.listStatus(new Path(path));
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println("\n");
            System.out.println(fileStatus);
            if (fileStatus.isFile()) {
                if (fileStatus.getLen() == 0) {
                    continue;
                }
                if ("SequenceFile".equalsIgnoreCase(fileType)) {
                    SequenceFile.Reader reader = new SequenceFile.Reader(distributedFileSystem, fileStatus.getPath(), configuration);
                    VarLongWritable varLongWritable = new VarLongWritable();
                    VectorWritable vectorWritable = new VectorWritable();
                    while (reader.next(varLongWritable, vectorWritable)) {
                        System.out.println(varLongWritable.get() + "\t" + vectorWritable.get());
                    }
                    reader.close();
                } else {
                    FSDataInputStream fsDataInputStream = distributedFileSystem.open(fileStatus.getPath());
                    byte[] bytes = new byte[fsDataInputStream.available()];
                    fsDataInputStream.read(bytes);
                    System.out.println(new String(bytes));
                    fsDataInputStream.close();
                }
            }
        }
    }
}