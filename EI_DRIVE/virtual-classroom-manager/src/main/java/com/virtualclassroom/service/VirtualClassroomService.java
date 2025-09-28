package com.virtualclassroom.service;

import com.virtualclassroom.exceptions.*;
import com.virtualclassroom.model.Classroom;

import java.util.*;

import java.util.logging.Logger;

public class VirtualClassroomService {
    private final Map<String, Classroom> classrooms;
    private final Logger logger;

    public VirtualClassroomService() {
        this.classrooms = new HashMap<>();
        this.logger = Logger.getLogger(VirtualClassroomService.class.getName());
    }

    public void addClassroom(String name) throws ClassroomAlreadyExistsException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Classroom name cannot be null or empty.");
        }
        if (classrooms.containsKey(name)) {
            throw new ClassroomAlreadyExistsException();
        }
        classrooms.put(name, new Classroom(name));
        logger.info("Classroom added: " + name);
    }

    public void removeClassroom(String name) throws ClassroomNotFoundException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Classroom name cannot be null or empty.");
        }
        if (classrooms.remove(name) == null) {
            throw new ClassroomNotFoundException();
        }
        logger.info("Classroom removed: " + name);
    }

    public List<String> listClassrooms() {
        List<String> classroomList = new ArrayList<>(classrooms.keySet());
        classroomList.sort(Comparator.naturalOrder());
        return classroomList;
    }

    public void addStudent(String studentId, String className) throws ClassroomNotFoundException, StudentAlreadyEnrolledException {
        Classroom classroom = getClassroom(className);
        classroom.addStudent(studentId);
        logger.info("Student " + studentId + " added to classroom " + className);
    }

    public List<String> listStudents(String className) throws ClassroomNotFoundException {
        Classroom classroom = getClassroom(className);
        List<String> studentList = new ArrayList<>(classroom.getStudents());
        studentList.sort(Comparator.naturalOrder());
        return studentList;
    }

    public void scheduleAssignment(String className, String title) throws ClassroomNotFoundException, AssignmentAlreadyExistsException {
        Classroom classroom = getClassroom(className);
        classroom.scheduleAssignment(title);
        logger.info("Assignment " + title + " scheduled for classroom " + className);
    }

    public void submitAssignment(String studentId, String className, String title) throws ClassroomNotFoundException, StudentNotEnrolledException, AssignmentNotFoundException, AlreadySubmittedException {
        Classroom classroom = getClassroom(className);
        try {
            classroom.submitAssignment(studentId, title);
            logger.info("Assignment " + title + " submitted by student " + studentId + " in classroom " + className);
        } catch (StudentAlreadyEnrolledException e) {
            throw new AlreadySubmittedException();
        }
    }

    public List<String> listAssignments(String className) throws ClassroomNotFoundException {
        Classroom classroom = getClassroom(className);
        List<String> assignmentList = new ArrayList<>(classroom.getAssignments());
        assignmentList.sort(Comparator.naturalOrder());
        return assignmentList;
    }

    public Set<String> getSubmissions(String className, String title) throws ClassroomNotFoundException {
        Classroom classroom = getClassroom(className);
        return classroom.getSubmissionsForAssignment(title);
    }

    public boolean isStudentEnrolled(String studentId, String className) throws ClassroomNotFoundException {
        if (studentId == null || studentId.isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }
        Classroom classroom = getClassroom(className);
        return classroom.getStudents().contains(studentId);
    }

    private Classroom getClassroom(String className) throws ClassroomNotFoundException {
        if (className == null || className.isEmpty()) {
            throw new IllegalArgumentException("Classroom name cannot be null or empty.");
        }
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ClassroomNotFoundException();
        }
        return classroom;
    }
}