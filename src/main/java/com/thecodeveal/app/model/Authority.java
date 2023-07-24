package com.thecodeveal.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Table(name="AUTH_AUTHORITY")
@Entity
public class Authority implements GrantedAuthority{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@ManyToMany(mappedBy = "authorities")

	


	public long getId() {
		return id;
	}





	public Authority() {
		super();
	}





	public void setId(long id) {
		this.id = id;
	}



	
	@Column(name = "USER_CODE")
	private String roleCode;
	

	public Authority(long id, String  roleCode) {
		this.id=id;
		this.roleCode = roleCode;
		
	}





	public String getRoleCode() {
		return roleCode;
	}



	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}


	@Override
	public String getAuthority() {
		return null;
	}
	
	

	
}
