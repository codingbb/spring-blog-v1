package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data   //getter, setter, toString
@Entity
@Table(name = "board_tb")
public class Board {
    @Id //프라이머리 키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_incrediment
    private int id;
    private String title;
    private String content;
    private int userId;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
