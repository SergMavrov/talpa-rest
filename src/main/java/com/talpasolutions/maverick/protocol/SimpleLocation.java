package com.talpasolutions.maverick.protocol;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@EqualsAndHashCode
public class SimpleLocation {

    @NotBlank
    protected String name;
    @NotBlank
    protected String address;
    @NotBlank
    protected Double latitude;
    @NotBlank
    protected Double longitude;

    /**
     * It can be DEPOT, STORE, CUSTOMER.
     */
    @NotBlank
    protected LocationType type;

}
