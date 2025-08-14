## Link Preview
http://ec2-3-24-255-157.ap-southeast-2.compute.amazonaws.com:8080/

## Group 2 - Hotel Manager
* Để phù hợp với yêu cầu đề bài, nhóm 2 quyết định làm lại toàn bộ project
*Toàn bộ lịch sử commit - changlog vẫn đươc lưu ở branch "old"*
- Những thay đổi chính trong project bao gồm: 
- Sử dụng 3 model
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
complete >
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

### Class Diagrams

**Base UML Diagram**
![Base UML Diagram](UML%20and%20stuffs/HotelMUMLbase.svg)

**Full UML Diagram**
![Full UML Diagram](UML%20and%20stuffs/HotelUMLL.svg)


## Activity Diagrams

Các activity diagrams được lưu trong thư mục: `UML and stuffs`

## Về Ứng dụng

Ứng dụng Quản lý Khách sạn được xây dựng với mục tiêu cung cấp một hệ thống quản lý phòng và đặt phòng hiệu quả. 
Ứng dụng cho phép người dùng thực hiện các chức năng như:

- Quản lý thông tin khách hàng
- Quản lý phòng
- Đặt phòng
- Kiểm tra tình trạng phòng

![Dashboard SS](UML%20and%20stuffs/DashboardSS.png)

Video Demo.
[Video](https://www.youtube.com/watch?v=a5xWk52TUK0)

## Change log 
  - adding H2 db to room model.
  - adding controller - frontend.
  - adding Reservation model, test
  - adding service layer for Reservation 
  - Refactor project structure to follow OOP.

## Fun stuffs 
Check changelog or Okimstupid.md.
