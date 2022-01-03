package com.example.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    private String id;

    private String name;

    private static final long serialVersionUID = 1L;
}