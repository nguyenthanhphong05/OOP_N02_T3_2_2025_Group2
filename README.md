## Nhóm 2
**Nguyễn Thanh Phong**  
**MSV:** 24100259

**Đặng Đức Kiên**  
**MSV:** 24100323

## Hotel Room Manager

**Hotel Room Manager** là một ứng dụng quản lý phòng khách sạn được phát triển bằng Java.  
Ứng dụng hỗ trợ quản lý thông tin phòng, tình trạng phòng, và các dịch vụ liên quan.  
Dự án với giao diện người dùng (GUI) được thiết kế bằng Java Swing . 

---

### Giới Thiệu
Hotel Room Manager là một công cụ quản lý phòng khách sạn với các tính năng chính như:

1. Quản lý thông tin phòng (số phòng, loại phòng, tình trạng phòng).
2. Đặt phòng và hủy phòng với ngày nhận/trả phòng.
3. Xem tình trạng phòng hiện tại (còn trống, đã đặt, đang sử dụng).
4. Tìm kiếm và xem danh sách đặt phòng theo tên khách hoặc ngày.
5. Kiểm tra phòng trống theo khoảng ngày.
6. Chỉnh sửa/xóa phòng.
7. Giao diện người dùng hiện đại (Material Design) với Java Swing (FlatLaf).
8. Giao diện dòng lệnh (Text-based UI) cho thao tác nhanh.

---

### Cấu Trúc Dự Án
```hotel-room-manager/
│
├── src/
│ ├── com/
│ │ ├── hotelroommanager/
│ │ │ ├── model/ # Các lớp mô hình dữ liệu như Room, Reservation
│ │ │ ├── service/ # Logic nghiệp vụ, xử lý dữ liệu
│ │ │ ├── ui/ # Các lớp liên quan tới giao diện Swing và Text-based
│ │ │ └── Main.java # Lớp chạy chính của ứng dụng
│
└── README.md # Tệp này
│
└── UML.txt # UML dạng text based
└── rooms.txt # data
└── reservations.txt # data
```

## UML
```
@startuml
class HotelRoomManager {
    - rooms: List<Room>
    - reservations: List<Reservation>
    + addRoom(room: Room): void
    + deleteRoom(room: Room): void
    + bookRoom(room: Room, customerDetails: String, checkIn: LocalDate, checkOut: LocalDate): Reservation
    + cancelBooking(reservation: Reservation): void
    + viewRooms(): List<Room>
    + getAvailableRooms(checkIn: LocalDate, checkOut: LocalDate): List<Room>
    + getReservations(): List<Reservation>
    + searchReservations(customer: String, date: LocalDate): List<Reservation
}

class Room {
    - roomNumber: String
    - roomType: String
    - roomStatus: String
    + getRoomInfo(): String
    + getRoomNumber(): String
    + getRoomType(): String
    + getRoomStatus(): String
    + setRoomStatus(status: String): void
}

class Reservation {
    - reservationID: String
    - room: Room
    - customerDetails: String
    - checkInDate: LocalDate
    - checkOutDate: LocalDate
    + getReservationDetails(): String
    + getReservationID(): String
    + getRoom(): Room
    + getCustomerDetails(): String
    + getCheckInDate(): LocalDate
    + getCheckOutDate(): LocalDate
}

class Main {
    + main(args: String[]): void
}

class HotelRoomManagerUI {
    - manager: HotelRoomManager
    - roomListModel: DefaultListModel<String>
    - roomList: JList<String>
    - outputArea: JTextArea
    - floorInput: JTextField
    - bookedCheck: JCheckBox
    - availableCheck: JCheckBox
    - singleCheck: JCheckBox
    - doubleCheck: JCheckBox
    - filterBtn: JButton
    + updateRoomList(): void
    + showRoomDetails(room: Room): void
    + showRoomOptions(room: Room): void
    + addRoom(): void
    + bookRoom(roomToBook: Room): void
    + removeRoom(roomToRemove: Room): void
    + showFilterPopup(): void
}

class TextbasedUI {
    + main(args: String[]): void
}

HotelRoomManager "1" *-- "many" Room
HotelRoomManager "1" *-- "many" Reservation
Main --> HotelRoomManagerUI
Main --> TextbasedUI
@enduml

```
### Activity Diagram

```
@startuml
:Book Room;
:Hiển thị danh sách phòng hiện có;
if (Danh sách phòng trống?) then (Có)
  :In thông báo "Không có phòng để đặt.";
  stop
else (Không)
  :In danh sách phòng hiện có;
  :Yêu cầu nhập số phòng muốn đặt;
  :Tìm phòng theo số nhập vào;
  if (Tìm thấy phòng?) then (Có)
    :Yêu cầu nhập tên khách hàng;
    :Yêu cầu nhập ngày nhận phòng;
    :Yêu cầu nhập ngày trả phòng;
    :Kiểm tra tính khả dụng của phòng;
    if (Phòng khả dụng?) then (Có)
      :Đặt phòng thành công;
      :In thông tin đặt phòng;
    else (Không)
      :In thông báo "Đặt phòng thất bại.";
    endif
  else (Không)
    :In thông báo "Không tìm thấy phòng.";
  endif
endif
stop
@enduml
```

```
@startuml
:Remove Room;
:Hiển thị danh sách phòng;
if (Danh sách trống?) then (Yes)
  :In thông báo "No rooms to remove";
  stop
else (No)
  :In danh sách phòng hiện tại;
  :Yêu cầu nhập số phòng cần xóa;
  :Tìm phòng theo số nhập vào;
  if (Tìm thấy?) then (Yes)
    :Xóa phòng khỏi danh sách;
    :In thông báo "Room removed";
  else (No)
    :In thông báo "Room not found";
  endif
endif
stop
@enduml
```






### Hướng Dẫn Sử Dụng

- Khi chạy ứng dụng, cửa sổ chính sẽ hiện ra danh sách các phòng.
- Bạn có thể sử dụng các nút trên giao diện để:
  1. Thêm phòng mới.
  2. Chỉnh sửa hoặc xóa thông tin phòng.
  3. Đặt phòng với ngày nhận/trả phòng hoặc hủy đặt phòng.
  4. Xem chi tiết từng phòng và tình trạng sử dụng.
  5. Xem danh sách đặt phòng, tìm kiếm theo tên khách hoặc ngày.
  6. Kiểm tra phòng trống theo khoảng ngày.

Giao diện Swing hỗ trợ tương tác trực tiếp với người dùng, các form nhập liệu và bảng thông tin rõ ràng.  
Giao diện dòng lệnh (Text-based UI) cho phép thao tác nhanh qua terminal.

---


##V1 Tính năng chính:

**Quản lý phòng:** Thêm, xóa phòng, xem danh sách phòng với trạng thái (đã đặt/chưa đặt), loại phòng (single/double), số tầng.

**Đặt phòng:** Đặt phòng cho khách, nhập tên khách, ngày nhận/trả phòng, kiểm tra trùng lịch.

**Hủy đặt phòng:** Hủy đặt phòng theo mã đặt phòng.

**Lọc phòng:** Lọc theo tầng, trạng thái (đã đặt/chưa đặt), loại phòng (single/double) bằng popup tiện lợi.

**Xem chi tiết:** Click vào phòng để xem chi tiết và thao tác nhanh (đặt/xóa phòng).

**Lưu dữ liệu:** Lưu thông tin phòng và đặt phòng vào file.

Giao diện "sắp" hiện đại: Giao diện Material Design, nền tối, dễ sử dụng.

Chạy được cả trên giao diện dòng lệnh (console) và giao diện đồ họa (GUI).

## Credits

**Engineered by @kiyosnguyen5**  
*Coded with GitHub Copilot
