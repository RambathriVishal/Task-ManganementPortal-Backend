package com.thecodeveal.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.thecodeveal.app.model.Register;

public interface RegisterRepo extends JpaRepository<Register,Integer>{

}
