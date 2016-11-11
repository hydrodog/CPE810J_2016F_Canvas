
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;

class Gettweets{
			 
		    public Gettweets() {
		 
		    }
		 
		    public Gettweets(int maxcountf,double[][] location, String storepath) {
		        this.location = location;   
		        this.maxcountf = maxcountf; //number of files
		        this.storepath=storepath;   //file path to store
		        this.filename = "H"+countf+".dat";  //file name
		        try {
					this.storeid=new BufferedWriter(new FileWriter(storepath+filename));
				} catch (IOException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
				}
		    }
		    public BufferedWriter getstorefile(){
		    	return storeid;
		    }
		    public long getcounth(){
		    	return counth;
		    }
		    public int getcountf(){
		    	return countf;
		    }
		    public void run() {        
		    	
				TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		        StatusListener listener = new StatusListener() {
		            public void onStatus(Status status) {    // current streaming status
		            	
		            	User user = status.getUser();        // get the user of the status 
		            	if(user!=null){
		            	   String Ulocation = user.getLocation();  //get the user's location from his profile
		            	   if(Ulocation!=null){
		            		   if(Ulocation.contains("Brooklyn")||Ulocation.contains("Brooklyn, NY")){  // selected the location contains keyword NY
		            		      long userId = user.getId();       //the user is selected and get his ID
		            		      System.out.println(userId+" "+Ulocation);
		            		      try{
				    					storeid.write(String.valueOf(userId));
				    					storeid.newLine();
				    					counth++;
				    				}catch (IOException e) {
			    						// TODO Auto-generated catch block
			    						e.printStackTrace();
			    					}
				    				if (counth>750) {              //set the number of users wanted******
			    					try {
										storeid.close();
										counth=0;
										countf++;
										if (countf>maxcountf){	
											System.exit(0);
										}
										filename = "H"+countf+".dat";
			    						storeid=new BufferedWriter(new FileWriter(storepath+filename));
			    					} catch (IOException e) {
			    						// TODO Auto-generated catch block
			    						e.printStackTrace();
			    					}
			    				}
		            		   
		            	   }
		            	   }
		            	}
//		            	GeoLocation geo=status.getGeoLocation();
//		            	HashtagEntity[] st=status.getHashtagEntities();
//		            	
//		            	if (st!=null){
//		    			String toktosave=status.getCreatedAt()+":::"+status.getUser().getScreenName()+":::";
//		    			if (geo!=null){
//		    				toktosave+=geo.getLatitude()+" "+geo.getLongitude();
//		    			}
//		    			
//		    			toktosave+=":::";
//		    			for(HashtagEntity token:st){
//		    				toktosave+=token.getText()+" ";
//		    			}
//		    				toktosave+=":::"+status.getText();
//		    				try{
//		    					storehashtags.write(toktosave);
//		    					storehashtags.newLine();
//		    					counth++;
//		    				}catch (IOException e) {
//	    						// TODO Auto-generated catch block
//	    						e.printStackTrace();
//	    					}
//		    				if (counth>2000000) {
//		    					try {
//									storehashtags.close();
//									counth=0;
//									countf++;
//									if (countf>maxcountf){	
//										System.exit(0);
//									}
//									filename = "H"+countf+".dat";
//		    						storehashtags=new BufferedWriter(new FileWriter(storepath+filename));
//		    					} catch (IOException e) {
//		    						// TODO Auto-generated catch block
//		    						e.printStackTrace();
//		    					}
//		    				}
//		    		//	}
//		            	
//		               System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
//		            	}
		            
		            }

		            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
		            }

		            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
		            }

		            public void onScrubGeo(long userId, long upToStatusId) {
		                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
		            }

		            public void onException(Exception ex) {
		                ex.printStackTrace();
		            }

					@Override
					public void onStallWarning(StallWarning arg0) {
						// TODO Auto-generated method stub
						
					}
		        };
		        twitterStream.addListener(listener); 
		        FilterQuery filterQuery = new FilterQuery();
		        filterQuery.locations(location);
		        twitterStream.filter(filterQuery);
				
		    }
		private int city;
	    private String name;
	    private int countf=1;
	    private long counth=0;
	    private int maxcountf;
	    private String storepath;
	    private String filename;
	    private BufferedWriter storeid;
	    private double[][] location=new double[2][2];
	}

