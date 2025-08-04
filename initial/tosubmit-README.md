# Submission Example - Hotel Management Demo

## Controller Location
`src/main/java/com/example/servingwebcontent/tosubmit/SubmitHotelController.java`

## View Location  
`src/main/resources/templates/tosubmit/hotel.html`

## URL to Access
`http://localhost:8080/tosubmit`

## Variables Used
- **customer**: Customer object with ID, name, email, phone
- **room**: Room type string (from Room object)
- **roomNumber**: Room number string  
- **reservation**: Reservation object with dates and IDs

## Models Demonstrated
1. **Customer**: CUST001, John Doe, john.doe@email.com, 123-456-7890
2. **Room**: Room 101, Deluxe Suite, $250/night, Available
3. **Reservation**: RES001, 3-day stay from today

## Spring Boot Features Shown
- @Controller annotation
- @GetMapping mapping
- Model attribute binding
- Thymeleaf template rendering
- Object property access in views

## Instructions to Run
1. Start the Spring Boot application: `./mvnw spring-boot:run`
2. Open browser to: `http://localhost:8080/tosubmit`
3. View the demo page showing all model data

---
**Ready for submission - Working demo of Spring Boot MVC with Hotel Management models**
