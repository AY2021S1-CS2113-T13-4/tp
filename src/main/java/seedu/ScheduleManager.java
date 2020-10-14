package seedu;
import seedu.task.Deadline;
import seedu.task.Event;
import seedu.task.Lesson;
import seedu.task.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * READ THIS FIRST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * Class for ScheduleManager.
 * Contains the schedule for the user.
 * We will assume that the ScheduleManager is built for AY 2020/2021 Semester 2.
 */
public class ScheduleManager {
	private static HashMap<LocalDate, ArrayList<Task>> semesterSchedule = new HashMap<>();
	//private static final String[] timing = {"08:00","09:00","10:00","11:00", "12:00", "13:00", "14:00", "15:00","16:00","17:00","18:00","19:00"
	//       ,"20:00","21:00","22:00","23:00"};
	/**
	 * Constructor for ScheduleManager if a ScheduleManager already exist.
	 * @param semesterSchedule
	 */
	public ScheduleManager(HashMap<LocalDate, ArrayList<Task>> semesterSchedule) {
		this.semesterSchedule = semesterSchedule;
	}

	/**
	 * Constructor for ScheduleManager if a ScheduleManager has yet to be created.
	 * Main role is to populate the HashMap with the dates in the year as the 'key'
	 * and an empty list of task as the value.
	 */
	public ScheduleManager() {
		this.semesterSchedule = new HashMap<>();
		// Now I will need to populate this hashmap because it is currently empty with no dates.
		for (LocalDate date = LocalDate.of(2020, 10, 12); date.isBefore(LocalDate.of(2021, 6, 1)); date = date.plusDays(1)) {
			this.semesterSchedule.put(date, new ArrayList<>());
		}
	}


	/**
	 * Add lessons to the day of the week that the lesson is conducted in.
	 * @param lesson lesson to be added to the schedule manager.
	 * TODO
	 *  - For future versions, need to check if there are any clash in timings first before adding.
	 */
	public void addLesson(Lesson lesson) {
		DayOfWeek day = lesson.getLessonDayInDayOfWeek();
		for (Map.Entry<LocalDate, ArrayList<Task>> entry : this.semesterSchedule.entrySet()) {
			LocalDate key = entry.getKey();
			if (key.getDayOfWeek().getValue() == day.getValue()) {
				this.semesterSchedule.get(key).add(lesson);
			}
		}
	}
	/**
	 * Add lessons on specific days
	 * @param lesson lesson to be added to the schedule manager.
	 */
	public void addLessonOnSpecificDays(Lesson lesson) {
		semesterSchedule.get(lesson.getDate()).add(lesson);
	}


	/**
	 * Deadline only got 1 day, so just filter for the
	 * date where I need to add the deadline,
	 * @param deadline add deadline inside the list of tasks of the schedule manager.
	 */
	public void addDeadline(Deadline deadline) {
		LocalDate date = LocalDate.parse(deadline.getDeadline());
		this.semesterSchedule.get(date).add(deadline);
	}

	/**
	 * Event only got 1 date, so just filter for the
	 * date where I need to add the event.
	 * @param event add event inside the list of tasks of the schedule manager.
	 * TODO
	 *  - for future versions, need if there is any clash of timings before adding.
	 *
	 */
	public void addEvent(Event event) {
		LocalDate date = LocalDate.parse(event.getDateOfEvent());
		this.semesterSchedule.get(date).add(event);
	}

	/**
	 * Displays tasks on the specific days.
	 * @param specificDate the specific day
	 * FIXME
	 *  - add code and output based on UG
	 *  - handle the task with frequency!
	 */
	public void display(LocalDate specificDate){
		ArrayList<Task> list =  semesterSchedule.get(specificDate);
		if (list.size()!=0){
			Ui.print("List of task on " + specificDate.toString() + " :");
			Ui.printListGenericType(list);
			Ui.printSeparator();
		} else {
			Ui.print("No Task on " + Ui.convertDateToString(specificDate));
		}
	}

	/*
	 * Displays tasks on the days within the range.
	 * The error message will be printed if startDay and endDay gives wrong range (e.g. endDay < startDay).
	 * @param startDate the start of the range.
	 * @param endDate the end of the range.
<<<<<<< HEAD
=======

>>>>>>> cc0ba615b22b5c9ceea2eea94404512ed9066d4c
	 * FIXME
	 *  - add code and output based on UG
	 *  - handle the task with frequency!
	 */


	public void editTask(String name, LocalDate date, String type, String newProperty){
		for(Task task :semesterSchedule.get(date)){

			switch(type) {
				case "description":
					if (task.getDescription().equals(name)) {
						task.setDescription(newProperty);
					}
					break;
				case "tasktype":
					if (task.getDescription().equals(name)) {
						task.setTasktype(newProperty);
					}
					break;

				case "module code":
					if (task.getDescription().equals(name)) {
						task.setModulecode(newProperty);
					}
					break;
				case "time":

					if (task.getDescription().equals(name)) {
						task.setTime(newProperty);
					}
					break;
				default:
					System.out.println("Invalid type");
			}
		}
	}

	public void editTask(String description, LocalDate date, String property, int [] newFrequency){
		for(Task task : semesterSchedule.get(date)){
			if(task.getDescription().equals(description)){
				task.setFrequency(newFrequency);
			}
		}
	}

	public void editTask(String description, LocalDate date, String property, LocalDate newDate){
		for(Task task : semesterSchedule.get(date)){
			if(task.getDescription().equals(description)){
				System.out.println(task.getDate());
				this.semesterSchedule.get(newDate).add(task);
			}
		}
		for(Task newTask : semesterSchedule.get(newDate)){
			if(newTask.getDescription().equals(description)){
				newTask.setDate(newDate.toString()); //Need to change later
			}
		}
		deleteTask(description,date);
	}


	public void deleteTask(String description, LocalDate date){
		if(semesterSchedule.get(date).size() != 0){
			semesterSchedule.get(date).removeIf(task -> task.getDescription().equals(description));
		}
		else{
			System.out.println("No task on this date");
		}
	}

	public void deleteTask(String description){
		for (LocalDate date = LocalDate.of(2020, 10, 12); date.isBefore(LocalDate.of(2021, 6, 1)); date = date.plusDays(1)) {
			if(semesterSchedule.get(date).size() != 0){
				semesterSchedule.get(date).removeIf(task -> task.getDescription().equals(description));
			}
		}
	}

	public void displayTodaySchedule(){
		LocalDate todayDate = LocalDate.now();
		Ui.print( "Today's Schedule:");
		ArrayList<Task> taskList = semesterSchedule.get(todayDate);
		ArrayList<Task> nonLessonList= new ArrayList<>();
		String[] timing = {"08:00", "09:00","10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
				"18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		for (Task t: taskList) {
			if (t instanceof Lesson){
				String startTime = ((Lesson) t).getStartTime();
				String endTime = ((Lesson) t).getEndTime();
				boolean hasStart = false;
				boolean hasEnd = false;
				for (int i = 0; i < timing.length; i++) {
					if (timing[i].substring(0, 5).equals(startTime) ) {
						hasStart = true;
						timing[i] = timing[i]+ " " + t.getDescription() + ", " + t.getModuleCode();
					} else if(timing[i].substring(0, 5).equals(endTime)) {
						hasEnd = false;
						hasStart = false;
						break;
					} if(hasStart && !hasEnd) {
						timing[i] = timing[i] + " " + t.getDescription() + ", " + t.getModuleCode();
					}
				}
			} else {
				nonLessonList.add(t);
			}
		}
		for (String i: timing){
			Ui.print(i);
		}
		Ui.print("\n Today's task:");
		Ui.printListGenericType(nonLessonList);
	}


	public void display(LocalDate startDate, LocalDate endDate){
		Ui.print("List of task from " + startDate.toString() + " to " + endDate.toString());
		for (LocalDate date = LocalDate.of(2020, 10, 12); date.isBefore(LocalDate.of(2021, 6, 1)); date = date.plusDays(1)) {
			if (date.isAfter(startDate) && date.isBefore(endDate)){
				if(semesterSchedule.get(date).size() != 0){
					Ui.print(date.format(DateTimeFormatter.ofPattern("MMM d"))
							+ " schedule :");
					Ui.printListGenericType(semesterSchedule.get(date));

				}
			}
		}
	}

	/**
	 * Method to display the schedule of 1 date.
	 * @param date that user wants to see the schedule of.
	 */
	public void displayDate(LocalDate date) {
		String startTime = null;
		String endTime = null;
		boolean taskIsLessonOrEvent = false;
		Ui.print("Here is your schedule on " + date.toString() + "!! :)");
		ArrayList<Task> taskList = semesterSchedule.get(date);
		ArrayList<Task> nonLessonList= new ArrayList<>();
		String[] timing = {"08:00", "09:00","10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
				"18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		for (Task t: taskList) {
			if (t instanceof Lesson) {
				startTime = ((Lesson) t).getStartTime();
				endTime = ((Lesson) t).getEndTime();
				taskIsLessonOrEvent = true;
			} else if (t instanceof Event) {
				startTime = ((Event) t).getStartTimeOfEvent();
				endTime = ((Event) t).getEndTimeOfEvent();
				taskIsLessonOrEvent = true;
			}
			if (taskIsLessonOrEvent == true) {
				boolean hasStart = false;
				boolean hasEnd = false;
				for(int i = 0; i < timing.length; i++){
					if (timing[i].substring(0,5).equals(startTime) ){
						hasStart = true;
						timing[i] = timing[i]+ " " + t.getDescription() + ", " + t.getModuleCode();
					} else if(timing[i].substring(0, 5).equals(endTime)){
						hasEnd = false;
						hasStart = false;
						break;
					} else if(hasStart && !hasEnd) {
						timing[i] = timing[i] + " " + t.getDescription() + ", " + t.getModuleCode();
					}
				}
			} else {
				nonLessonList.add(t);
			}
		}
		for (String i: timing){
			Ui.print(i);
		}
		Ui.print("\nDeadlines on " + date.toString() + ":");
		Ui.printListGenericType(nonLessonList);
	}

	/**
	 * Displays today's tasks.
	 * FIXME
	 *  - add code and output based on UG
	 *  - handle the task with frequency!
	 */
	public void display() {
	}

	/**
	 * update the schedule upon adding new task through ModuleManager
	 */
	public static void updateSchedule(LocalDate date, Task task){
		semesterSchedule.get(date).add(task);
	}
}

