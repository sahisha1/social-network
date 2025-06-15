

mini_project_social_network.java
Java
Private comments
package mini_pro;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
//comparator for sorting text posts based on likes
class LikeComparator implements Comparator<TextPost> {
	@Override
	public int compare(TextPost t1, TextPost t2) {
		return Integer.compare(t2.getLikes(), t1.getLikes());
	}
}
//class for Text posts
class TextPost {
	private int postId;
	String id;//id of user who made the post
	String content;
	int likes;
	private static int postCounter = 0;//to create unique post Ids
	ArrayList<Student> LikedBy=new ArrayList<Student>();//list of users who liked the post
	ArrayList<Tag> Tags;//list of tags added to the post by the user
	public TextPost(String content, ArrayList<Tag> Tags) {
		this.content=content;
		this.likes = 0;
		this.Tags = new ArrayList<>();
		this.postId = ++postCounter; // Increment and assign unique ID

		this.Tags=Tags;

	}
	//get likes
	public int getLikes() {
		return likes;
	}
	public void addTag(Tag tag) {
		Tags.add(tag);

	}
	public void like() {
		likes++;
	}

	public void delete() {
		// Logic to delete the post can be handled in the managing class
		System.out.println("Post deleted: " + content);
	}
	public void display() {
		System.out.println("**********************************");
		System.out.println("Post ID: " + postId);
		System.out.println("Content: " + content);
		System.out.println("Likes: " + likes);
		System.out.print("Tags: ");
		for (Tag tag : Tags) {
			System.out.print(tag.getName() + ", ");
		}
		System.out.println("\n");
		System.out.println("**********************************");
}
	public int getPostId() {
		return postId;	}
}



class Tag {



	private String tagLabel;



	public Tag(String name) {



		tagLabel=name;



	}
	public String getName() {

		return tagLabel;
	}
}

//student class



class Student {
	Scanner scanner=new Scanner(System.in);
	int id;

	String name;
	String password;
	public static ArrayList<TextPost> allPosts = new ArrayList<TextPost>();//static will mean that it stores all the posts made by all the users
	ArrayList<TextPost> CreatedPosts; //list of created textposts



	//ArrayList<Tag> FilteredTags;//list of tags the user does not wish to see


	public Student(int id, String name, String password) {

		this.id=id;

		this.name=name;

		this.password=password;

		CreatedPosts=new ArrayList<TextPost>();

	}

	Student() {
	}

	public Student(int id, String password) {

		this.id=id;

		this.password=password;
	}

//By default, toString() returns gibberish,


//but here we override it to return a more meaningful representation.

	@Override
	public String toString() {

		return id + " (" + name + ")";

	}


//method to verify password

	public boolean verifyPassword(String password) {
		return this.password.equals(password);

	}

	public void createPost(String content) {
		System.out.print("Enter comma separated tags: ");
		String inputTags[]=scanner.nextLine().split(",");
		ArrayList<Tag> tags=new ArrayList<Tag>();
		//creating ArrayList of Tags from the inputTags[]
		for(String currentTag: inputTags) {
			Tag ct=new Tag(currentTag);
			tags.add(ct);
		}
		
		TextPost newtp=new TextPost(content, tags);

		CreatedPosts.add(newtp);

		allPosts.add(newtp);
	}

	//view posts
	public void viewPosts() {
		//sorting in descending to get top 10 posts
		Collections.sort(allPosts, (t1, t2) -> Integer .compare(t1.getLikes(), t2.getLikes()));
		int l=Math.min(allPosts.size(), 10);
		for(int i=0; i<l; i++) {

			allPosts.get(i).display();

		}

	}

	public void viewOwnPosts() {

		for(TextPost t : CreatedPosts) {

			t.display();

		}
	}

	public void viewAllPosts() {



		for(TextPost t: allPosts) {

			t.display();

		}
	}

	//delete posts



	//like a post



	public void likePost(int likeID, Student self) {



		viewAllPosts();

		Collections.sort(allPosts, (t1, t2) -> Integer .compare(t1.getLikes(), t2.getLikes()));

		int target=likeID;



		int left = 0; // Starting index



		int right = CreatedPosts.size() - 1; // Ending index



		boolean found=false;
		while (left <= right) {
			int mid = left + (right - left) / 2; // Calculate the middle index

			TextPost current=CreatedPosts.get(mid);



// Check if the target is present at mid



			if (CreatedPosts.get(mid).getPostId() == target) {



//post found
				current.likes++;

				current.LikedBy.add(self);
				System.out.println("Post Found and Liked");

				found=true;

			}

// If target is greater, ignore the left half



			if (current.getPostId() < target) {
				left = mid + 1;
			}



// If target is smaller, ignore the right half
			else {
				right = mid - 1;
			}
			if(!found) {
				System.out.println("ID not found");
			}	}
	}

}
class Admin extends Student {
	private String username;
	private String password;
	public Admin(int id, String name, String username, String password) {

		super(id, name, password); // Call the parent class (Student) constructor
		this.username = username;
		this.password = password;
	}
	public boolean authenticate(String username, String password) {



		return this.username.equals(username) && this.password.equals(password);
	}



	@Override



	public String toString() {
		return super.toString() + " (Admin)";
	}
}



class CampusConnect {



//Adjacency list for friendships



	private Map<Integer, Set<Integer>> graph;



//Stores student details with student id as key



	 Map<Integer, Student> students;



//hash map for messager history



//messagehistory to store record of history



	private Map<Integer,List<messages>> messagehistory;



//instance of admin class used for authentication below



	private Admin admin;



	public CampusConnect(String adminUsername, String adminPassword) {



		graph = new HashMap<>();



		students = new HashMap<>();



		messagehistory=new HashMap<>();



		this.admin = new Admin(0, "Administrator", adminUsername, adminPassword);



		// Hardcode students and their connections
		hardcodeData();
	}
//Hardcode students and their connections
	private void hardcodeData() {
		// Adding student
		Student student1 = new Student(1, "Ananya", "Ananya@1234");
		Student student2 = new Student(2, "Bunty", "Bunty@1234");
		Student student3 = new Student(3, "Disha", "Disha@1234");
		Student student4 = new Student(4, "Priya", "Priya@1234");
		Student student5 = new Student(5, "Harsh", "Harsh@1234");
		Student student6 = new Student(6, "Kartik", "Karthik@1234");
		addStudent(student1);
		addStudent(student2);
		addStudent(student3);
		addStudent(student4);
		addStudent(student5);
		addStudent(student6);
		// Adding connections (variety of connections)
		addConnection(1, 2);
		addConnection(1, 3);
		addConnection(2, 4);
		addConnection(2, 5);
		addConnection(3, 5);
		addConnection(4, 6);
		addConnection(5, 6);
	}
//authenticate student
	public boolean authenticateStudent(int ID, String pw) {
		Student temp=students.get(ID);
		if(temp!=null) {
			return temp.verifyPassword(pw);
		}
		return false;
	}
//checking if student exists for student login
	public boolean studentExists(int studentId) {
		return students.containsKey(studentId);
	}
	public void findInfluencer() {
	    int maxConnections = -1;
	    int influencerId = -1;
	    for (Map.Entry<Integer, Set<Integer>> entry : graph.entrySet()) {
	        int studentId = entry.getKey();
	        int connectionCount = entry.getValue().size();
	        if (connectionCount > maxConnections) {
	            maxConnections = connectionCount;
	            influencerId = studentId;
	        }
	    }
	    if (influencerId != -1) {
	        System.out.println("The influencer is Student " + influencerId + " (" + students.get(influencerId).name + 
	                           ") with " + maxConnections + " connections.");
	    } else {
	        System.out.println("No influencer found.");
	    }
	}
	public void recommendFriends(int studentId) {
		  if (!graph.containsKey(studentId)) {
		      System.out.println("Student " + studentId + " does not exist in the system.");
		      return;
		  }
		  // Get the set of direct friends for the student

		  Set<Integer> directFriends = graph.get(studentId);
		  Map<Integer, Integer> mutualFriendCount = new HashMap<>();
		  // Loop through each direct friend
		  for (Integer friendId : directFriends) {
		      // Get friends of each friend (i.e., friends of friends)

		      Set<Integer> friendsOfFriend = graph.get(friendId);
		      for (Integer potentialFriendId : friendsOfFriend) {
		          // Avoid recommending the student to themselves or an existing friend
		          if (potentialFriendId != studentId && !directFriends.contains(potentialFriendId)) {
		              // Count the number of mutual friends with each potential friend
		        	  int count = 0;
		        	  if (mutualFriendCount.containsKey(potentialFriendId)) {
		        	      count = mutualFriendCount.get(potentialFriendId);
		        	  }
		        	  mutualFriendCount.put(potentialFriendId, count + 1);

		          }

		      }

		  }
		  
		  if (mutualFriendCount.isEmpty()) {

			    System.out.println("No friends to recommend for student " + studentId + ".");

			} else {

			    System.out.println("Recommended friends for student " + studentId + ":");

			    for (Map.Entry<Integer, Integer> entry : mutualFriendCount.entrySet()) {

			        Integer recommendedFriendId = entry.getKey();

			        Integer mutualFriends = entry.getValue();

			        System.out.println("Student " + recommendedFriendId + " with " + mutualFriends + " mutual friend(s).");

			    }

			}


		}
	public void addStudent(Student student) {
		if (student.id < 0) {
			System.out.println("Student ID cannot be negative.");
			return;
		}
		if (!students.containsKey(student.id)) {
			students.put(student.id, student);
			graph.put(student.id, new HashSet<>());
			messagehistory.put(student.id, new ArrayList<>());//initialise a blank list for messages
		} else {
			System.out.println("Student with ID " + student.id + " already exists.");
		}
	}
//Connecting two students
	public void addConnection(int id1, int id2) {
		if (!students.containsKey(id1) || !students.containsKey(id2)) {
			System.out.println("One or both students not found.");
			return;
		}
		if (id1 == id2) {
			System.out.println("Cannot connect a student to themselves.");
			return;
		}
		graph.get(id1).add(id2);
		graph.get(id2).add(id1);
	}
//Removing a student
	public void removeStudent(int id) {
		if (students.containsKey(id)) {
			graph.remove(id);
			for (Set<Integer> connections : graph.values()) {
				connections.remove(id);
			}
			students.remove(id);
			System.out.println("Student ID " + id + " removed.");
		} else {
			System.out.println("Student not found.");
		}
	}
//View all student connections
	public void viewAllConnections() {
		for (Map.Entry<Integer, Set<Integer>> entry : graph.entrySet()) {
			System.out.println("Student " + entry.getKey() + " (" + students.get(entry.getKey()).name + ") -> " + entry.getValue());
		}
	}
	public void viewIsolatedStudents() {
		for (Map.Entry<Integer, Set<Integer>> entry : graph.entrySet()) {
			if (entry.getValue().isEmpty()) {
				System.out.println("Isolated Student: " + entry.getKey() + " (" + students.get(entry.getKey()).name + ")");
				return;
			}
		}
		System.out.println("No Student are isolated");
	}
//View mutual connections between two students
	public void viewMutualConnections(int id1, int id2) {
		if (!students.containsKey(id1) || !students.containsKey(id2)) {
			System.out.println("One or both students not found.");
			return;
		}
		Set<Integer> mutualConnections = new HashSet<>(graph.get(id1));
		mutualConnections.retainAll(graph.get(id2));
		if (mutualConnections.isEmpty()) {
			System.out.println("No mutual connections between Student " + id1 + " and Student " + id2 + ".");
		} else {
			System.out.println("Mutual connections between Student " + id1 + " and Student " + id2 + ": " + mutualConnections);
		}
	}

//Admin authentication
	int adminAccess(String username, String password) {
		//check if username n password are same as its stored value
		if (admin.authenticate(username, password)) {
			System.out.println("Admin access granted.");
			return 1;
		} else {
			System.out.println("Invalid admin credentials.");
		}
		return 0;
	}
	public void sendmessage(int senderId, int receiverId, String message_content) {
		// Create a new message
		messages ob = new messages(senderId, receiverId, message_content);
		// Add message to sender's list
		if (messagehistory.get(senderId) == null) {
			messagehistory.put(senderId, new ArrayList<>());
		}
		messagehistory.get(senderId).add(ob);
		// Add message to receiver's list
		if (messagehistory.get(receiverId) == null) {
			messagehistory.put(receiverId, new ArrayList<>());
		}
		messagehistory.get(receiverId).add(ob);
		System.out.println("Message sent successfully.");
	}
	/*public void delete(int senderid, int recieverid, String message_content) {
	// Find and remove the message from sender's history
	List<messages> senderMessages = messagehistory.get(senderid);
	if (senderMessages != null) {
	for (int i = 0; i < senderMessages.size(); i++) {
	messages msg = senderMessages.get(i);
	if (msg.getrecieverid() == recieverid && msg.message_content.equals(message_content)) {
	senderMessages.remove(i);
	System.out.println("Message deleted from sender's history.");
	return; // Exit after deleting
	}
	}
	}
	// Find and remove the message from receiver's history
	List<messages> receiverMessages = messagehistory.get(recieverid);
	if (receiverMessages != null) {
	for (int i = 0; i < receiverMessages.size(); i++) {
	messages msg = receiverMessages.get(i);
	if (msg.getsenderid() == senderid && msg.message_content.equals(message_content)) {
	receiverMessages.remove(i);
	System.out.println("Message deleted from receiver's history.");
	return; // Exit after deleting
	}
	}
	}
	System.out.println("Message not found.");
	}
	*/
	//view messages
	public void viewmessage(int id1, int id2) {
		Scanner scanner = new Scanner(System.in);
		// Check if the message history exists for the sender (id1)
		if (!messagehistory.containsKey(id1)) {
			System.out.println("Student not found in message history");
			return;
		}
		List<messages> mess = messagehistory.get(id1);
		System.out.println("Message History between " + id1 + " and " + id2 + ":");
		// Display all messages between the two students
		for (messages msg : mess) {
			if (msg.getrecieverid() == id2 || msg.getsenderid() == id2) {
				msg.display(); // Display the message
			}
		}
		// Ask if the user wants to mark all unread messages as read
		System.out.print("Would you like to mark all unread messages as read? (y/n): ");
		// Scanner scanner = new Scanner(System.in);
		String choice = scanner.nextLine();
		// Mark unread messages as read if the user chooses 'y'
		if (choice.equalsIgnoreCase("y")) {
			for (messages msg : mess) {
				if ((msg.getrecieverid() == id2 || msg.getsenderid() == id2) && !msg.isRead()) {
					msg.markasRead();
				}
			}
		}
		scanner.nextLine();
		// Ask if the user wants to delete a message
		System.out.print("Would you like to delete a message? (y/n): ");
		String ch = scanner.nextLine();
		//scanner.nextLine();
		if (ch.equalsIgnoreCase("y")) {
			System.out.print("Enter the message content to delete: ");
			String messageContent = scanner.nextLine();
			delete(id1, id2, messageContent); // Call delete method with the entered message content
		}
		//scanner.close(); // Close the scanner after use
	}
	public void delete(int senderid,int recieverid,String message_content) {
		// message exist in sender history
		List<messages> sendmessages=messagehistory.get(senderid);
		if(sendmessages==null) {
			System.out.println("No messages found");
			return;
		}
		//if exist remove it from sender side
		for(int i=0; i<sendmessages.size(); i++) {
			messages ob = sendmessages.get(i);
			if(ob.getrecieverid()==recieverid && ob.message_content.equals(message_content)) {
				sendmessages.remove(i);
				System.out.println("Messages deleted successfull!!");
				break;
			}
		}
		List<messages> recievermessages=messagehistory.get(recieverid);
		if(recievermessages==null) {
			System.out.println("No messages found");
			return;
		}
		//if exist remove it from receiver side
		for(int i=0; i<sendmessages.size(); i++) {
			messages ob = recievermessages.get(i);
			if(ob.getsenderid()==senderid && ob.message_content.equals(message_content)) {
				recievermessages.remove(i);
				System.out.println("Messages deleted successfull!!");
				break;
			}
		}
	}
}
//messages
class messages {
	int senderid; //sender id
	int recieverid; // reciever id
	String message_content;
	LocalDateTime timestamp;
	boolean read;
	private static final Map<String, String> emojiMap = new HashMap<>();
	static {
        emojiMap.put(":)", "😊");
        emojiMap.put(":(", "☹️");
        emojiMap.put(":heart:", "❤️");
        emojiMap.put(":smile:", "😊");
        emojiMap.put(":sad:", "☹️");
        emojiMap.put(":thumbsup:", "👍");
        
    }
    
	messages(int senderid,int recieverid,String message_content) {
		this.senderid=senderid;
		this.recieverid=recieverid;
		this.message_content=message_content;
		this.timestamp = LocalDateTime.now();
	}
	
	public messages() {
		// TODO Auto-generated constructor stub
	}
	public boolean isRead() {
		return read;
	}
	public void markasRead() {
		this.read = true;
	}
	//to get who as send message
	public int getsenderid() {
		return senderid;
	}
	// to get whom ie receiver
	public int getrecieverid() {
		return recieverid;
	}
	private String convertToEmoji(String text) {
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }
        return text;
    }
	public void display() {
	   System.out.println("----- Message Details -----");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println("Timestamp: " + timestamp.format(formatter));
		System.out.println("From: "+senderid );
		System.out.println("To: "+recieverid );
		System.out.println("Message: " + convertToEmoji(message_content));
		//System.out.println("Message:"+message_content);
		//System.out.println("Timestamp: " + timestamp.format(formatter));
		  System.out.println("---------------------------");
	}
	
}
public class mini_project_social_network {
	public static void main(String[] args) {
		CampusConnect campus = new CampusConnect("admin", "1234");
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n--- CampusConnect ---");
		System.out.println("Who are you?");
		System.out.println("1.Admin");
		System.out.println("2.Student");
		int ch=scanner.nextInt();
boolean conswitch=true;

while(conswitch) {
		switch(ch)
		{
		case 1:
			System.out.print("Enter Admin Username: ");
			String adminUsername = scanner.next();
			System.out.print("Enter Admin Password: ");
			String adminPassword = scanner.next();
			int access=campus.adminAccess(adminUsername, adminPassword);
			if (access==1)
			{
				while (true) {

					System.out.println("\n--- CampusConnect Menu ---");
					System.out.println("1. Add Student");
					System.out.println("2. Add Connection");
					System.out.println("3. Remove Student");
					System.out.println("4. View All Connections");
					System.out.println("5. View Isolated Students");
					System.out.println("6. View Mutual Connections");
					System.out.println("7. Student Login");
					System.out.println("8. Find Influencar");
					System.out.println("9. Recommendations");
					System.out.println("10. Exit");
					System.out.print("Enter your choice: ");
					int choice = scanner.nextInt();
					scanner.nextLine(); // Consume newline
					switch (choice) {
					case 1: // Add Student
						System.out.print("Enter Student ID: ");
						int studentId = scanner.nextInt();
						scanner.nextLine();
						System.out.print("Enter Student Name: ");
						String studentName = scanner.nextLine();
						String pw=studentName+"@1234";
						campus.addStudent(new Student(studentId, studentName, pw));
						break;
					case 2: // Add Connection
						System.out.print("Enter First Student ID: ");
						int id1 = scanner.nextInt();
						System.out.print("Enter Second Student ID: ");
						int id2 = scanner.nextInt();
						campus.addConnection(id1, id2);
						break;
					case 3: // Remove Student
						System.out.print("Enter Student ID to Remove: ");
						int removeId = scanner.nextInt();
						campus.removeStudent(removeId);
						break;
					case 4: // View All Connections
						System.out.println("All Connections:");
						campus.viewAllConnections();
						break;
					case 5: // View Isolated Students
						System.out.println("Isolated Students:");
						campus.viewIsolatedStudents();
						break;
					case 6: // View Mutual Connections
						System.out.print("Enter First Student ID: ");
						int mutualId1 = scanner.nextInt();
						System.out.print("Enter Second Student ID: ");
						int mutualId2 = scanner.nextInt();
						campus.viewMutualConnections(mutualId1, mutualId2);
						break;
					case 7:
						System.out.println("You have selected Student Login.");
						System.out.print("Enter Student ID: ");
						int studentIdLogin = scanner.nextInt();
						scanner.nextLine();
						if (campus.studentExists(studentIdLogin)) {
							System.out.print("Enter your password: ");
							String studentPassword = scanner.nextLine();
							if (campus.authenticateStudent(studentIdLogin, studentPassword)) {
								System.out.println("Login successful! Welcome, " + studentIdLogin);
								Student student=campus.students.get(studentIdLogin);
								studentMenu(campus, student, scanner, studentIdLogin); // Call student menu
							} else {
								System.out.println("Invalid password. Please try again.");
							}
						} else {
							System.out.println("Student ID not found.");
						}
						break;
					case 8:
						//find student with maximum connection
						campus.findInfluencer();
						break;
						
					case 9:
						System.out.println("Enter Student ID ");
						int id=scanner.nextInt();
						System.out.println("Friend Recommendations for student: ");
						campus.recommendFriends(id);
					case 10: // Exit
						System.out.println("Exiting CampusConnect. Goodbye!");
						scanner.close();
						return;
					default:
						System.out.println("Invalid choice! Please try again.");
					}
				}
// 1 switch end
			}
// if break
			break;
		case 2:
			System.out.println("You have entered as a Student");
			System.out.print("Enter your user id: ");
			int inputID=scanner.nextInt();
			scanner.nextLine();
			if(campus.studentExists(inputID)) {
				System.out.println("Student ID found. Proceeding now.");
				System.out.print("Enter your password: ");
				String inputPassword=scanner.nextLine();
				//authenticate the password and student
				if(campus.authenticateStudent(inputID, inputPassword)) {
					System.out.println("Login succesful! Welcome, " + inputID);

					//CampusConnect campus1 = new CampusConnect(inputID, inputPassword);

					//Student student=new Student(inputID, inputPassword);
					Student student=campus.students.get(inputID);
					System.out.println("\n--- Student Menu ---");
					System.out.println("1.Posts\n2.Chats\n3.Exit Student\n");
					int postOrChoice=scanner.nextInt();
					scanner.nextLine();
					switch(postOrChoice) {
					case 1:
						boolean continuePosts=true;
						while(continuePosts) {
							System.out.print("Enter choice:\n1.Create Post\n2.View Posts\n3.Like Post\n4.Exit");
							int postChoice=scanner.nextInt();
							scanner.nextLine();
							switch(postChoice) {
							case 1:
								//create post
								System.out.print("Enter post content: ");
								String content=scanner.nextLine();
								student.createPost(content);
								break;

							case 2:
								//view posts
								student.viewPosts();
								break;

							case 3:
								//like a post
								System.out.print("Enter post ID to like: ");
								int likeId=scanner.nextInt();
								scanner.nextLine();
								student.likePost(likeId, student);
								break;
							/*case 4:
								//delete post
								System.out.print("Enter post ID to delete: ");
								int deleteId=scanner.nextInt();
								scanner.nextLine();
								student.deletePost(deleteId);
						break;
*/
							case 4:
								//stop seeing posts
								continuePosts=false;
break;
							default:
								System.out.println("Wrong choice. Try again.");
								break;
//subswitch  postall
							}
//while
						}
//case 1:post
						break;
						
					case 2:
						int menuChoice =0;
						boolean continueChatting=true;
						while (continueChatting) {
							try {
								System.out.println("\n--- Student Menu ---");
								System.out.println("1. Send Message");
								System.out.println("2. View Messages");
								System.out.println("3. Exit");
								System.out.print("Enter your choice: ");
								if(scanner.hasNextInt()) {
									menuChoice = scanner.nextInt();
									scanner.nextLine();
								} else {
									System.out.println("Invalid input. Please enter a number.");
									scanner.nextLine();
									continue;
								}
								// Consume newline
								switch (menuChoice) {
								case 1: // Send Message
									System.out.print("Enter Recipient ID: ");
									int receiverId = scanner.nextInt();
									scanner.nextLine(); // Consume the newline character after nextInt()
									System.out.print("Enter Message Content: ");
									String content = scanner.nextLine();
									campus.sendmessage(inputID, receiverId, content);
									break;
								case 2: // View Messages
									System.out.print("Enter Student ID to view messages with: ");
									int viewId = scanner.nextInt();
									scanner.nextLine(); // Consume the newline character after nextInt()
									campus.viewmessage(inputID, viewId);
									break;
								case 3: // Exit
									System.out.println("Exiting Student Menu.");
									return;
								default:
									System.out.println("Invalid choice! Please try again.");
//subswitch
								}
							
							//try
							} catch (InputMismatchException e) {
								System.out.println("Invalid input. Please enter a number.");
								scanner.nextLine(); // Clear invalid input
							} catch (Exception e) {
								System.out.println("An error occurred: " + e.getMessage());
								e.printStackTrace();
							}

// chatting while 
						}
break;					
					/*case 3:
						//recommendations of friends
						campus.recommendFriends(inputID);
						break;*/
					case 3:
						System.out.println("Exiting Student Menu: ");
						break;
					}

//if
				}
				else {
					System.out.println("Wrong password entered!");
				}
				break;
			}
		}
		}
	}


	private static void studentMenu(CampusConnect campus, Student student, Scanner scanner, int studentId) {
		int menuChoice;
		System.out.println("\n--- Student Menu ---");
		System.out.println("1.Posts\n2.Chats\n3.Recommendations\n4.Exit Student\n");
		int postOrChoice=scanner.nextInt();
		scanner.nextLine();
		switch(postOrChoice) {
		case 1:
			boolean continuePosts=true;
			while(continuePosts) {
				System.out.print("Enter choice:\n1.Create Post\n2.View Posts\n3.Like Post\n4.Exit");
				int postChoice=scanner.nextInt();
				scanner.nextLine();
				switch(postChoice) {
				case 1:
					//create post
					System.out.print("Enter post content: ");
					String content=scanner.nextLine();
					student.createPost(content);
					break;

				case 2:
					//view posts
					student.viewPosts();
					break;

				case 3:
					//like a post
					System.out.print("Enter post ID to like: ");
					int likeId=scanner.nextInt();
					scanner.nextLine();
					student.likePost(likeId, student);
					break;
				/*case 4:
					//delete post
					System.out.print("Enter post ID to delete: ");
					int deleteId=scanner.nextInt();
					scanner.nextLine();
					student.deletePost(deleteId);
			break;
*/
				case 4:
					//stop seeing posts
					continuePosts=false;
break;
				default:
					System.out.println("Wrong choice. Try again.");
					break;
//subswitch  postall
				}
//while
			}
//case 1:post
			break;
		case 2:
boolean continuechat=true;
		while (continuechat) {
			System.out.println("\n--- Student Menu ---");
			System.out.println("1. Send Message");
			System.out.println("2. View Messages");
			System.out.println("3. Exit");
			System.out.print("Enter your choice: ");
			menuChoice = scanner.nextInt();
			scanner.nextLine(); // Consume newline
			switch (menuChoice) {
			case 1: // Send Message
				System.out.print("Enter Recipient ID: ");
				int receiverId = scanner.nextInt();
				scanner.nextLine(); // Consume newline
				System.out.print("Enter Message Content: ");
				String content = scanner.nextLine();
				campus.sendmessage(studentId, receiverId, content);
				break;
			case 2: // View Messages
				System.out.print("Enter Student ID to view messages with: ");
				int viewId = scanner.nextInt();
				campus.viewmessage(studentId, viewId);
				break;
			case 3: // Exit
				System.out.println("Exiting Student Menu.");
continuechat=false;
			default:
				System.out.println("Invalid choice! Please try again.");
			}
		}
	}
	}}
//"I'm doing well, thank you! :smile: :heart:"
//"Hello! How are you? :)"
mini_project_social_network.java
Displaying mini_project_social_network.java.