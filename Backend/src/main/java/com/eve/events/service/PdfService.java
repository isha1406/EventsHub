package com.eve.events.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eve.events.Exceptions.ResourceNotFoundException;
import com.eve.events.entity.BookingItems;
import com.eve.events.entity.Bookings;
import com.eve.events.entity.Users;
import com.eve.events.repository.BookingRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PdfService {

	@Autowired
	private BookingRepository bookingRepository;

	public ByteArrayInputStream createPdf(int bookingId, int bookingItemId) {

		log.info("Creating PDF for bookingId: {} and bookingItemId: {}", bookingId, bookingItemId);
		Bookings bookings = this.bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found by this Id"));

		Users user = bookings.getUser();
		List<BookingItems> events = bookings.getBookingItemsList();

		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String eventName = "";
		LocalDate eventDate = LocalDate.now();
		LocalTime eventTime = LocalTime.now();
		String eventLocation = "";
		int noOfTickets = 0;
		double ticketPrice = 0;

		for (BookingItems e : events) {
			if (e.getBookingItemId() == bookingItemId) {
				eventName = e.getEvents().getEventName();
				eventDate = e.getEvents().getDate();
				eventTime = e.getEvents().getTime();
				eventLocation = e.getEvents().getLocation();
				noOfTickets = e.getNoOfTickets();
				ticketPrice = e.getBookingEventsPrice();
			}
		}

		String title = "ENJOY YOUR EVENT";
		String content ="Name: "+ firstName+" "+lastName+"\r\n"+"Event Name: " + eventName + "\r\n" + "Date: " + eventDate + "\r\n" + "Time: " + eventTime
				+ "\r\n" + "Location: " + eventLocation + "\r\n" + "No Of Tickets: " + noOfTickets + "\r\n"
				+ "TicketPrice: " + ticketPrice;

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, out);
		document.open();

		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		titleFont.setSize(18);
		Paragraph titlePara = new Paragraph(title, titleFont);
		titlePara.setAlignment(Element.ALIGN_CENTER);
		document.add(titlePara);

		Font paraFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		paraFont.setSize(12);
		Paragraph paragraph = new Paragraph(content, paraFont);
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		document.close();

        log.info("PDF created successfully.");
		return new ByteArrayInputStream(out.toByteArray());
	}
}
