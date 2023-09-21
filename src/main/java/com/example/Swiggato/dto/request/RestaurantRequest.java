package com.example.Swiggato.dto.request;

import com.example.Swiggato.Enum.RestarauntCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {

    String name;

    String location;

    RestarauntCategory restrauntCategory;

    String contactNumber;

}
