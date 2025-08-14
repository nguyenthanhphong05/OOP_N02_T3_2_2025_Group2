## Group 2 - Hotel Manager
* Để phù hợp với yêu cầu đề bài, nhóm 2 quyết định làm lại toàn bộ project
*Toàn bộ lịch sử commit - changlog vẫn đươc lưu ở branch "old"*
- Những thay đổi chính trong project bao gồm: 
- Sử dụng 2 model: Room và Booking thay vì 3 model 
- Sử dụng java Springboot framework 
- Sử dụng H2 làm cơ sở dữ liệu (JPA)

# Phân chia công việc 
- Phân chia các task cho từng thành viên trong nhóm

**Nguyễn Thanh Phong - 24100259**
- Thiết kế cơ sở dữ liệu
- Triển khai - hoàn thiện Model Room
- Triển khai model Reservations 
- Thiết kế workflow.
- Lên Project construction.
- Thiết kế - hoàn thiện Front End. 
- Viết main docx - UML

**Đặng Đức Kiên - 24100323**
- Triển khai - **hoàn thiện** Model Booking
- Triển khai services Booking
- Viết Unit test cho Model Room
- Lên kế hoạch kiểm thử cho toàn bộ hệ thống
- Thiết kế Frontend với Bootstrap

## Project Construction

```
> data 
>> src 
    >> model
        >> Room.java (hoàn thiện)
        >> Reservation.java (hoàn thiện)
        >> Customer.java (hoàn thiện)
    >> repository
        >> RoomRepository.java
        >> ReservationRepository.java
        >> CustomerRepository.java
    >> service
        >> RoomService.java
        >> ReservationService.java
        >> CustomerService.java
    >> controller
        >> RoomController.java
        >> ReservationController.java
        >> CustomerController.java
        >> MainController.java (Redirect /rooms at this moment)
    >> resources
        >> templates 
            >> customer-form.html
            >> customer.html
            >> rooms.html
            >> reservations.html
            >> index.html
    >> test
> HotelManagementApplication.java


```
Main path : OOP_N02_T3_2_2025_Group2\complete\src\main\java\com\g2\hotelm\HotelManagementApplication.java


## UML 
[Base UML Diagram](OOP_N02_T3_2_2025_Group2\UML and stuffs\HotelMUMLbase.svg)
[Full UML Diagram](OOP_N02_T3_2_2025_Group2\UML and stuffs\HotelUMLL.svg)


## Acitivy Diagram 
Path
OOP_N02_T3_2_2025_Group2\UML and stuffs

## Change log 
  - adding H2 db to room model.
  - adding controller - frontend.
  - adding Reservation model, test
  - adding service layer for Reservation 
  - Refactor project structure to follow OOP.

## Fun stuffs 
Check changelog or Okimstupid.md.
