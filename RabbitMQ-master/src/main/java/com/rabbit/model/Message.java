package com.rabbit.model;


 
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Justin Mathew
 *
 * Created On 15-Mar-2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message implements Serializable  {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 7630717693995440669L;
	private String message;
	 private Date createdOn;
}
