package com.eve.events.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eve.events.service.PdfService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api/pdf")
public class PdfController {

	@Autowired
	private PdfService pdfService;

	@GetMapping("/createpdf/{bookingId}/{bookingItemId}")
	public ResponseEntity<InputStreamResource> createPdf(@PathVariable int bookingId, @PathVariable int bookingItemId) {
		log.info("Creating PDF for bookingId: {} and bookingItemId: {}", bookingId, bookingItemId);
		try {
			InputStreamResource file = new InputStreamResource(pdfService.createPdf(bookingId, bookingItemId));

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=eventsHub.pdf")
					.contentType(MediaType.APPLICATION_PDF).body(file);
		} catch (Exception e) {
			log.error("Error while creating PDF", e);
			throw e;
		}
	}

}
