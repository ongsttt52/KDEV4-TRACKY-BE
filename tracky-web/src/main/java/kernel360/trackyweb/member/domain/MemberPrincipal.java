package kernel360.trackyweb.member.domain;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kernel360.trackyweb.member.domain.entity.MemberEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberPrincipal implements UserDetails {

	private final MemberEntity memberEntity;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + memberEntity.getRole().toUpperCase()));
	}

	@Override
	public String getPassword() {

		return memberEntity.getPwd();
	}

	@Override
	public String getUsername() {

		return memberEntity.getMemberId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public MemberEntity getMember() {
		return memberEntity;
	}
}
