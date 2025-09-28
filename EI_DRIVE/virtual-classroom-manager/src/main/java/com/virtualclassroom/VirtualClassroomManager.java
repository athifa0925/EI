package com.virtualclassroom;

import com.virtualclassroom.commands.*;
import com.virtualclassroom.exceptions.*;
import com.virtualclassroom.service.VirtualClassroomService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VirtualClassroomManager {
    private static final Logger logger = Logger.getLogger(VirtualClassroomManager.class.getName());
    private static final String RUNNING = "RUNNING";
    private static final String EXIT = "EXIT";

    public static void main(String[] args) {
        VirtualClassroomService service = new VirtualClassroomService();
        Scanner scanner = new Scanner(System.in);

        String currentRole = null;
        String currentStudentId = null;
        String currentClassName = null;
        Map<String, Command> commandMap = null;

        System.out.println("Welcome to Virtual Classroom Manager");
        while (currentRole == null) {
            System.out.println("Enter role (assigner or student, or type 'exit' to quit): ");
            String roleInput = scanner.nextLine().trim().toLowerCase();
            if (roleInput.equals("exit")) {
                System.out.println("Exiting Virtual Classroom Manager.");
                scanner.close();
                return;
            }
            try {
                if (!roleInput.equals("assigner") && !roleInput.equals("student")) {
                    throw new InvalidRoleException();
                }
                String studentId = null;
                String className = null;
                if (roleInput.equals("student")) {
                    System.out.println("Enter student ID: ");
                    studentId = scanner.nextLine().trim();
                    if (studentId.isEmpty()) {
                        throw new IllegalArgumentException("Student ID cannot be empty.");
                    }
                    System.out.println("Enter class name: ");
                    className = scanner.nextLine().trim();
                    if (className.isEmpty()) {
                        throw new IllegalArgumentException("Class name cannot be empty.");
                    }
                    if (!service.isStudentEnrolled(studentId, className)) {
                        throw new StudentNotEnrolledException();
                    }
                }
                currentRole = roleInput;
                currentStudentId = studentId;
                currentClassName = className;
                commandMap = initializeCommands(service, currentRole, currentStudentId, currentClassName);
                displayAvailableCommands(currentRole);
            } catch (InvalidRoleException e) {
                System.out.println("Error: " + e.getMessage());
                logger.log(Level.SEVERE, "Error during initial role selection", e);
            } catch (ClassroomNotFoundException e) {
                System.out.println("Error: Classroom not found.");
                logger.log(Level.SEVERE, "Classroom not found during student role setup", e);
            } catch (StudentNotEnrolledException e) {
                System.out.println("Error: Student not enrolled in this classroom.");
                logger.log(Level.SEVERE, "Student not enrolled during student role setup", e);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                logger.log(Level.SEVERE, "Error during student role setup", e);
            } catch (Exception e) {
                System.out.println("Error: Unexpected error during role setup.");
                logger.log(Level.SEVERE, "Unexpected error during role setup", e);
            }
        }

        String programState = RUNNING;
        System.out.println("Enter commands (type 'exit' to quit):");
        while (programState.equals(RUNNING)) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            String cmdName = parts[0];
            if (cmdName.equals("exit")) {
                System.out.println("Exiting Virtual Classroom Manager.");
                programState = EXIT;
                continue;
            }
            try {
                if (cmdName.equals("change_role")) {
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Usage: change_role <type>");
                    }
                    String newRole = parts[1].toLowerCase();
                    if (!newRole.equals("assigner") && !newRole.equals("student")) {
                        throw new InvalidRoleException();
                    }
                    String newStudentId = null;
                    String newClassName = null;
                    if (newRole.equals("student")) {
                        System.out.println("Enter student ID: ");
                        newStudentId = scanner.nextLine().trim();
                        if (newStudentId.isEmpty()) {
                            throw new IllegalArgumentException("Student ID cannot be empty.");
                        }
                        System.out.println("Enter class name: ");
                        newClassName = scanner.nextLine().trim();
                        if (newClassName.isEmpty()) {
                            throw new IllegalArgumentException("Class name cannot be empty.");
                        }
                        if (!service.isStudentEnrolled(newStudentId, newClassName)) {
                            throw new StudentNotEnrolledException();
                        }
                    }
                    currentRole = newRole;
                    currentStudentId = newStudentId;
                    currentClassName = newClassName;
                    commandMap = initializeCommands(service, currentRole, currentStudentId, currentClassName);
                    System.out.println("Role changed to " + newRole + ".");
                    logger.info("Role changed to " + newRole);
                    displayAvailableCommands(currentRole);
                    continue;
                }
                Command command = commandMap.get(cmdName);
                if (command == null) {
                    throw new IllegalArgumentException("Unknown command: " + cmdName + " for your role.");
                }
                String[] cmdArgs = Arrays.copyOfRange(parts, 1, parts.length);
                command.execute(cmdArgs);
            } catch (InvalidRoleException | IllegalArgumentException | StudentNotEnrolledException | ClassroomNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
                logger.log(Level.SEVERE, "Error processing command: " + e.getMessage(), e);
            } catch (Exception e) {
                System.out.println("Error: Unexpected error processing command.");
                logger.log(Level.SEVERE, "Unexpected error processing command", e);
            }
            System.out.println("\nEnter commands (type 'exit' to quit):");
        }
        scanner.close();
    }

    private static Map<String, Command> initializeCommands(VirtualClassroomService service, String role, String studentId, String className) {
        Map<String, Command> commandMap = new HashMap<>();
        if (role.equals("assigner")) {
            commandMap.put("add_classroom", new AddClassroomCommand(service));
            commandMap.put("add_student", new AddStudentCommand(service));
            commandMap.put("schedule_assignment", new ScheduleAssignmentCommand(service));
            commandMap.put("remove_classroom", new RemoveClassroomCommand(service));
            commandMap.put("list_classrooms", new ListClassroomsCommand(service));
            commandMap.put("list_students", new ListStudentsCommand(service));
            commandMap.put("list_assignments", new ListAssignmentsCommand(service));
        } else if (role.equals("student")) {
            commandMap.put("list_assignments", new StudentListAssignmentsCommand(service, className));
            commandMap.put("submit_assignment", new StudentSubmitAssignmentCommand(service, studentId, className));
        }
        return commandMap;
    }

    private static void displayAvailableCommands(String role) {
        System.out.println("Available commands for " + role + " role:");
        if (role.equals("assigner")) {
            System.out.println("- add_classroom <name> : Create a new classroom");
            System.out.println("- add_student <student_id> <class_name> : Enroll a student in a classroom");
            System.out.println("- schedule_assignment <class_name> <assignment_title> : Schedule an assignment");
            System.out.println("- remove_classroom <name> : Remove a classroom");
            System.out.println("- list_classrooms : List all classrooms with student counts");
            System.out.println("- list_students <class_name> : List students in a classroom");
            System.out.println("- list_assignments <class_name> : List assignments with submission status");
        } else if (role.equals("student")) {
            System.out.println("- list_assignments : List all assignments in your classroom");
            System.out.println("- submit_assignment <assignment_title> : Submit an assignment");
        }
        System.out.println("- change_role <type> : Change role to assigner or student");
        System.out.println("- exit : Quit the application");
    }
}