
package com.virtualclassroom.commands;

import com.virtualclassroom.service.VirtualClassroomService;

public class AddClassroomCommand implements Command {
    private final VirtualClassroomService service;

    public AddClassroomCommand(VirtualClassroomService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: add_classroom <name>");
        }
        service.addClassroom(args[0]);
        System.out.println("Classroom " + args[0] + " has been created.");
    }
}