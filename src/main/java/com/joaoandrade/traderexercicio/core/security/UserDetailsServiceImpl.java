package com.joaoandrade.traderexercicio.core.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joaoandrade.traderexercicio.domain.model.Trader;
import com.joaoandrade.traderexercicio.domain.repository.TraderRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private TraderRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Trader> obj = repository.findByEmail(username);

		if (obj.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}

		Trader trader = obj.get();
		return new TraderAutenticado(trader.getId(), trader.getEmail(), trader.getSenha(), trader.getPerfis());
	}

}
