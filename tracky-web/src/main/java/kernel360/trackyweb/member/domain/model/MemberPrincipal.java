package kernel360.trackyweb.member.domain.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kernel360.trackyweb.member.infrastructure.entity.Member;

public class MemberPrincipal implements UserDetails {

	private final Member member;

	public MemberPrincipal(Member member) {
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// role = "admin" → "admin"으로 그대로 인식되도록
		return List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole().toUpperCase()));
	}

	@Override
	public String getPassword() {
		return member.getPwd();
	}

	@Override
	public String getUsername() {
		return member.getMemberId();
	}

	@Override public boolean isAccountNonExpired() {return true;}
	@Override public boolean isAccountNonLocked() {return true;}
	@Override public boolean isCredentialsNonExpired() {return true;}
	@Override public boolean isEnabled() {return true;}

	public Member getMember() {
		return member;
	}
}
