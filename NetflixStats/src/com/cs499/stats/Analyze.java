package com.cs499.stats;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;

public class Analyze {

	public static void main(String[] args) throws Exception {
		
		HashMap<Integer, Integer> ratingsSum = new HashMap<>();
		HashMap<Integer, Integer> ratingsCount = new HashMap<>();
		HashMap<Integer, String> movieTitles = new HashMap<>();
		PriorityQueue<Pair> topRatings = new PriorityQueue<>();
		PriorityQueue<Pair> topUsers = new PriorityQueue<>();
		
		//load movie titles
		BufferedReader br = new BufferedReader(new FileReader("movie_titles.txt"));
		String line = "";
		while((line=br.readLine())!=null)
		{
			String[] s = line.split(",");
			movieTitles.put(Integer.valueOf(s[0]), s[2]);
		}
		br.close();
		br = new BufferedReader(new FileReader("ratingsSum"));
		//load sum of ratings
		while((line=br.readLine())!=null) {
			String[] s = line.split("\\s");
			ratingsSum.put(Integer.valueOf(s[0]), Integer.valueOf(s[1]));
		}
		br.close();
		br = new BufferedReader(new FileReader("ratingsCount"));
		//load count of ratings
		while((line=br.readLine())!=null) {
			String[] s = line.split("\\s");
			ratingsCount.put(Integer.valueOf(s[0]), Integer.valueOf(s[1]));
		}
		//run through the entries, get average, throw in priorityQueue
		Iterator it = ratingsSum.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        double average =  (1.0*(Integer)pair.getValue())/ratingsCount.get(pair.getKey());
	        Pair p = new Pair((Integer)pair.getKey(), average);
	        topRatings.add(p);
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    br.close();
	    br = new BufferedReader(new FileReader("reviewsCount"));
	    //get the top 10 users with most reviews
	    while((line=br.readLine())!=null) {
			String[] s = line.split("\\s");
			Pair p = new Pair(Integer.valueOf(s[0]), Double.valueOf(s[1]));
			topUsers.add(p);
		}
	    br.close();
	    //output results
	    System.out.println("==== Top 10 Movies With Highest Rating====");
	    for(int i = 1; i <= 10; ++i) {
	    	System.out.print(i + ".) ");
	    	Pair p = topRatings.poll();
	    	System.out.print("[" + movieTitles.get(p.getId()) + "] : ");
	    	System.out.printf("%.3f%n", p.getData());
	    }
	    System.out.println("==== Top 10 Users With Highest Reviews====");
	    for(int i = 1; i <= 10; ++i) {
	    	System.out.print(i + ".) ");
	    	Pair p = topUsers.poll();
	    	System.out.println("[" + p.getId() + "] : " + (int)p.getData());
	    }	    
	}
	public static class Pair implements Comparable<Pair> {
		
		private int id;
		private double data;
		
		public Pair(int id, double data) {
			this.id = id;
			this.data = data;
		}
		public int getId() {
			return id;
		}
		public double getData() {
			return data;
		}
		public int compareTo(Pair other) {
			//max priority queue
			if(data < other.data) {
				return 1;
			} else if(data > other.data) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
