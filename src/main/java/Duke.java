import  java.util.Scanner;

public class Duke {

    static final int MAX_NO_OF_TASKS = 100;

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);

        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");

        Task[] userTasks = new Task[MAX_NO_OF_TASKS];
        int taskCounter = 0;
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();

        while(!userInput.equals("bye")){
            if(userInput.equals("list")){
                System.out.println("Here are the tasks in your list:");
                for (int counter = 0; counter < taskCounter; counter++) {
                    System.out.println((counter+1) + ".["+ userTasks[counter].getStatusIcon()
                            +"] " + userTasks[counter].getDescription());
                }
            } else {
                String[] individualWords = userInput.trim().split("\\s+");
                int activityNumber=-1;
                if(individualWords.length==2 && individualWords[0].equals("done")){
                    System.out.println("yo1:");
                    try {
                        activityNumber = Integer.parseInt(individualWords[1]);
                        userTasks[activityNumber-1].setTaskStatus(true);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("   ["+ userTasks[activityNumber-1].getStatusIcon() +"] " + userTasks[activityNumber-1].getDescription());
                    } catch (NumberFormatException e) {
                        System.out.println("added: " + userInput);
                        userTasks[taskCounter] = new Task(userInput);
                        taskCounter++;
                    }
                } else {
                    System.out.println("added: " + userInput);
                    userTasks[taskCounter] = new Task(userInput);
                    taskCounter++;
                }
            }
            userInput = in.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}
