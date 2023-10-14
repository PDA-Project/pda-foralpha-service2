package com.example.legendfive.overall.Entity;

import com.example.legendfive.common.Time;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "quiz_record")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizRecord extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_record_id")
    private Long id;

    @GeneratedValue(generator = "uuid2")
    @Type(type = "uuid-char")
    @Column(name="quiz_record_uuid")
    private UUID quizRecordUuid;

    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "quiz_record_fk_user_id"))
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @Column(name = "is_public", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean isWon;

    @Enumerated(EnumType.STRING)
    private Quiz quiz;

}