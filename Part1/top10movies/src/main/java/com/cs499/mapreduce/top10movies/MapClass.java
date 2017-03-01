package com.cs499.mapreduce.top10movies;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class MapClass extends Mapper<LongWritable, Text, Text, IntWritable>{
	
    private Text word = new Text();
    
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		
		String line = value.toString();
		StringTokenizer st = new StringTokenizer(line,",");
		
		word.set(st.nextToken());
		st.nextToken();
		context.write(word, new IntWritable(Double.valueOf(st.nextToken()).intValue()));
		
	}

}
