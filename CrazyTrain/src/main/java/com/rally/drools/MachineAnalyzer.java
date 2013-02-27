/*
 * Author - Niranjan Patil
 * Purpose - Rally Software Internship Assignment
 * Date - 02/26/2013
 * 
 */


package com.rally.drools;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

/**
 * This is a sample class to launch a rule.
 */
public class MachineAnalyzer {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
           
       
            
            // Create machine configurations for first  machines
            
            Machine [] m = new Machine[5];
            m[0] = new Machine("Machine1", 2, 5, "none", 800);
            m[1] = new Machine("Machine2", 8, 3, "average", 1500);
            m[2] = new Machine("Machine3", 1, 1, "none", 400);
            m[3] = new Machine("Machine4", 16, 4, "premium", 3000);
            m[4] = new Machine("Machine5", 32, 5, "none", 8000);
            
            // Create machine configuration for machine 6
            Machine m6 = new Machine("Machine6", 2, 2, "premium", 5500);
            
            // Add the machines to the knowledge session
            
            for(int i=0;i<m.length;i++)
            {
            	 ksession.insert(m[i]);
            }
            
            ksession.insert(m6);
            
            // Fire all the rules on the machines
            
            ksession.fireAllRules();
            
            boolean hotgamingcomputers=true;
            int riskynumbercrunches =0;
            
            
            // Print the conclusions for the first part
            
            System.out.println("Conclusions..........................");
            
            for(int i=0;i<m.length;i++)
            {
            	if(m[i].isGameflag())
            	{
            		System.out.println(m[i].getName()+" is a safe gaming computer");
            	}
            	
            	
            	if(m[i].isCrunchflag() && m[i].isRiskyflag())
            	{
            		System.out.println("Number crunchers that is risky"+m[i].getName());
            		riskynumbercrunches++;
            	}
            	
            	
            	
            	if(m[i].isGameflag() && ! m[i].isHotflag())
            	{
            		hotgamingcomputers= false;
            	}
            		
            	
            }
            
            if(riskynumbercrunches==0)
            {
            	System.out.println("None of the number crunches are risky");
            }
            
            if(hotgamingcomputers)
            {
            	System.out.println("All gaming computers are hot.");
            }
            else
            {
            	System.out.println("All gaming computers are not hot.");
            }
            
            
            // Print the conclusions about machine 6
            
            System.out.println("\nConclusions about Machine 6..... ");
            
            if(m6.isLoudflag())
            System.out.println(m6.getName() + " is Loud");
                
            if(m6.isCheapflag())
            System.out.println(m6.getName() + " is cheap");
            
            if(m6.isExpensiveflag())
            System.out.println(m6.getName() + " is Expensive");
            
            
            if(m6.isGameflag())
            System.out.println(m6.getName() + " can game");
            
            
            if(m6.isHotflag())
            System.out.println(m6.getName() + " is hot");
            
            if(m6.isRiskyflag())
            System.out.println(m6.getName() + " is Risky");
            
            if(m6.isCrunchflag())
            System.out.println(m6.getName() + " can crunch numbers");
            
            if(m6.isCheapflag())
            System.out.println(m6.getName() + " is cheap");
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // Method to create the knowledge session
    
    private static KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("MachineRules.drl"), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }

  
    
    // Class that represent Machines
    
    public static class Machine {
    	
    	
    	// Class variable declarations
    	
    	private String name;
		private int memory;
        private int speed;
        private String videoCard;
        private int price;
        private boolean loudflag=false;
  

		private boolean cheapflag=false;
        private boolean expensiveflag=false;
        private boolean hotflag= false;
        private boolean riskyflag= false;
        private boolean gameflag= false;
        private boolean crunchflag= false;
        
         
        // Parameterized constructor to initialize machine objects.
        
        public Machine(String name, int memory, int speed, String videoCard,
    				int price) {
    			super();
    			this.name = name;
    			this.memory = memory;
    			this.speed = speed;
    			this.videoCard = videoCard;
    			this.price = price;
    		}
        
        
        // Getter and Setter methods for class variables
        
        public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getMemory() {
			return memory;
		}

		public void setMemory(int memory) {
			this.memory = memory;
		}

		public int getSpeed() {
			return speed;
		}

		public void setSpeed(int speed) {
			this.speed = speed;
		}

		public String getVideoCard() {
			return videoCard;
		}

		public void setVideoCard(String videoCard) {
			this.videoCard = videoCard;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}
		
		  public boolean isLoudflag() {
				return loudflag;
			}

			public void setLoudflag(boolean loudflag) {
				this.loudflag = loudflag;
			}

			public boolean isCheapflag() {
				return cheapflag;
			}

			public void setCheapflag(boolean cheapflag) {
				this.cheapflag = cheapflag;
			}

			public boolean isExpensiveflag() {
				return expensiveflag;
			}

			public void setExpensiveflag(boolean expensiveflag) {
				this.expensiveflag = expensiveflag;
			}

			public boolean isHotflag() {
				return hotflag;
			}

			public void setHotflag(boolean hotflag) {
				this.hotflag = hotflag;
			}

			public boolean isRiskyflag() {
				return riskyflag;
			}

			public void setRiskyflag(boolean riskyflag) {
				this.riskyflag = riskyflag;
			}

			public boolean isGameflag() {
				return gameflag;
			}

			public void setGameflag(boolean gameflag) {
				this.gameflag = gameflag;
			}

			public boolean isCrunchflag() {
				return crunchflag;
			}

			public void setCrunchflag(boolean crunchflag) {
				this.crunchflag = crunchflag;
			}


    }

}
