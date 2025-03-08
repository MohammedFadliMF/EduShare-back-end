package com.edushare.edushare_backend.entities;

import com.edushare.edushare_backend.enums.FileType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ContentId;
    private String fileName;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Lob
    private byte[] fileContent;

}
