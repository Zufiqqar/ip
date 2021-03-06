package duke.file;

import duke.exception.DukeException;
import duke.task.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
Class Storage for handling file read and write operations
*/
public class Storage {
    protected String relativeFileName;
    protected ArrayList<Task> userTasks;
    protected final String SEPARATOR = "|";

    /*
    Constructor for Storage Object
    Initialize relativeFileName, userTasks variables
    */
    public Storage(String filePath) {
        relativeFileName = filePath;
        userTasks = new ArrayList<>();
    }

    /*
    Function that read a text file
    Initialize Task object and return it
    */
    public ArrayList<Task> load() throws DukeException{
        char taskSelection;
        try {
            File dukeFileRead = new File(relativeFileName);
            if (dukeFileRead.createNewFile()) {
                //Create file if it does not exist
            }
            Scanner myReader = new Scanner(dukeFileRead);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                StringTokenizer tokens = new StringTokenizer(data , SEPARATOR);
                tokens.nextToken();
                taskSelection = data.toLowerCase().charAt(0);
                switch(taskSelection){
                case 't':
                    createTodoTask(userTasks, tokens);
                    break;
                case 'd':
                    createDeadlineTask(userTasks, tokens);
                    break;
                case 'e':
                    createEventTask(userTasks, tokens);
                    break;
                default:
                    System.out.println("Unrecognizable task type. Skipped this particular task object creation.");
                }
            }
            myReader.close();
        }catch (FileNotFoundException e) {
            throw new DukeException("File cannot be found.");
        }catch (IOException e) {
            throw new DukeException("I/O Operations got errors. Data cannot be read/");
        }catch (NoSuchElementException e) {
            throw new DukeException("The file is empty.");
        }
        return userTasks;
    }

    private void createEventTask(ArrayList<Task> userTasks, StringTokenizer tokens) {
        boolean isDone = Boolean.parseBoolean(tokens.nextToken());
        String description = tokens.nextToken();
        String eventLocation = tokens.nextToken();
        userTasks.add(new Event(description, eventLocation));
        userTasks.get(userTasks.size()-1).setTaskStatus(isDone);
    }

    private void createDeadlineTask(ArrayList<Task> userTasks, StringTokenizer tokens) {
        boolean isDone = Boolean.parseBoolean(tokens.nextToken());
        String description = tokens.nextToken();
        String deadlineDate = tokens.nextToken();
        userTasks.add(new Deadline(description, deadlineDate));
        userTasks.get(userTasks.size()-1).setTaskStatus(isDone);
    }

    private void createTodoTask(ArrayList<Task> userTasks, StringTokenizer tokens) {
        boolean isDone = Boolean.parseBoolean(tokens.nextToken());
        String description = tokens.nextToken();
        userTasks.add(new Todo(description));
        userTasks.get(userTasks.size()-1).setTaskStatus(isDone);
    }

    /*
    Function that writes back to the text file
    */
    public void dump(TaskList userCurrentTasks){
        String newLineToBeInserted = null;
        try {
            FileWriter dukeFileWrite = new FileWriter(relativeFileName);
            for(Task userTask : userCurrentTasks.getUserTasks()){
                switch(userTask.getTaskType()){
                case 't':
                case 'T':
                    newLineToBeInserted = convertTodoDetailsToString(userTask);
                    break;
                case 'd':
                case 'D':
                    newLineToBeInserted = convertDeadlineDetailsToString(userTask);
                    break;
                case 'e':
                case 'E':
                    newLineToBeInserted = convertEventDetailsToString(userTask);
                    break;
                default:
                    System.out.println("Unrecognizable task type. Please recreate the task.");
                }
                dukeFileWrite.write(newLineToBeInserted + "\n");
            }
            dukeFileWrite.close();

        } catch (IOException e) {
            System.out.println("An IO Operations Error has occurred. Data is not saved.");
        }
    }

    private String convertEventDetailsToString(Task userTask) {
        Event event = (Event) userTask;
        return event.getTaskType() + "|" + event.getTaskStatus() + "|" +
                event.getDescription() + "|" + event.getEventLocation();
    }

    private String convertDeadlineDetailsToString(Task userTask) {
        Deadline deadline = (Deadline) userTask;
        return deadline.getTaskType() + "|" + deadline.getTaskStatus() + "|" +
                deadline.getDescription() + "|" + deadline.getDeadlineDate();
    }

    private String convertTodoDetailsToString(Task userTask) {
        return userTask.getTaskType() + "|" + userTask.getTaskStatus() + "|" + 
                userTask.getDescription();
    }
}
