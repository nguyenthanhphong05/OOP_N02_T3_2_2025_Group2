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
- Đang trong quá trình sửa lại cấu trúc theo dạng OOP thay vì data centric.
```
> data 
>> src 
    >> model
        >> Room.java (hoàn thiện)
        >> Reservation.java (hoàn thiện)
    >> repository
        >> RoomRepository.java
        >> ReservationRepository.java
    >> service
        >> RoomService.java
        >> ReservationService.java
    >> controller
        >> RoomController.java
        >> ReservationController.java
        >> HomeController.java (Redirect /rooms at this moment)
    >> resources
        >> css 
        >> html
    >> test
```

## UML 
Update UML Diagram
Từ procedural -> OOP 


## Diagram 

## Change log 
  - adding H2 db to room model.
  - adding controller - frontend.
  - adding Reservation model, test

## Fun stuffs 
Check changelog or Okimstupid.md.
