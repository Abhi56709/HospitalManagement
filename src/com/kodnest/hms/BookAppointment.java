package com.kodnest.hms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookAppointment {
	private Connection connection;
	private Patient patient;
	private Doctor doctor;
	
	Scanner sc= new Scanner(System.in);
	public BookAppointment(Connection connection, Patient patient, Doctor doctor) {
		this.connection= connection;
		this.patient= patient;
		this.doctor=doctor;
	}
	
public void bookAppointment() throws SQLException {
	System.out.println("Enter patient Id");
	int patientId = sc.nextInt();
	
	System.out.println("Enter Doctor Id");
	int doctorId = sc.nextInt();
	
	System.out.println("Enter Appointment data yyyy-mm-dd");
	String appointmentDate = sc.next();
	
	if(!patient.getPatientById(patientId)) {
		System.out.println("Please provide  valid patient id.");
		return;
	}
	
	if(!doctor.getDoctorById(doctorId)) {
		System.out.println("Please provide  valid doctor id.");
		return;
	}
	
	if (!checkAvailability(connection, doctorId, appointmentDate)) {
		System.out.println("Doctor not available.");
		return;
	}
	String query = "insert into appointments(patient_id, doctor_id, appointment_date) values(?, ?, ?)";
	
	 try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		 preparedStatement.setInt(1, patientId);
	    	preparedStatement.setInt(2, doctorId);
	    	preparedStatement.setString(3, appointmentDate);
	    	
	    	if (preparedStatement.executeUpdate() > 0) {
	    		System.out.println("Appointment bookd successfully");
	    	} else {
	    		System.out.println("Appointment not bookd successfully");
	    	}
	 }
	
}
public boolean checkAvailability(Connection connection, int doctorID, String appointmentDate) throws SQLException {
	String query = "select count(1) from appointments where doctor_id = ? and appointment_date =?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    	preparedStatement.setInt(1, doctorID);
    	preparedStatement.setString(2, appointmentDate);
    	
    	try (ResultSet rs = preparedStatement.executeQuery()) {
    		if(rs.next()) {
        		return rs.getInt(1) == 0;
        	}
    	}
    }
	return false;
    
	
}
}
