package com.alkemy.wallet.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="users")
public class User {
	
	@Id
	@Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "{firstname.notnull}")
	@Column(name = "first_name", nullable=false)
	private String firstName;

	@NotEmpty(message = "{lastname.notnull}")
	@Column(name = "last_name", nullable=false)
	private String lastName;

	@Email(message = "{email.pattern}")
	@NotEmpty(message = "{email.notnull}")
	@Column(name = "email", unique=true, nullable=false)
	private String email;

	@NotEmpty(message = "{password.notnull}")
	@Column(name = "password", nullable=false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role; // Clave foranea hacia ID de Role

	@Column(name = "creation_date")
	@CreationTimestamp
	private Date creationDate;

	@Column(name = "update_date")
	@UpdateTimestamp
	private Date updateDate;

	@Column(name = "soft_delete")
	private boolean softDelete = false;

}
