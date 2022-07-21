package tn.esprit.wellness.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.wellness.entity.User;
import tn.esprit.wellness.repository.UserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User getUser(long id) {
		return userRepository.getById((long) id);
	}

}
