package kernel360.trackyweb.member.domain.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kernel360.trackyweb.member.infrastructure.entity.MemberEntity;

public class MemberPrincipal implements UserDetails {

	private final MemberEntity memberEntity;

	public MemberPrincipal(MemberEntity memberEntity) {

		this.memberEntity = memberEntity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		// DB의 role 값: "admin" 또는 "super_admin"
		// Security에서 hasAuthority("admin") / hasAuthority("super_admin") 로 체크 가능
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + memberEntity.getRole().toUpperCase()));

		// role = "admin" → "admin"으로 그대로 인식되도록
		//return List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole().toUpperCase()));
	}

	@Override
	public String getPassword() {

		return memberEntity.getPwd();
	}

	@Override
	public String getUsername() {

		return memberEntity.getMemberId();
	}

	@Override public boolean isAccountNonExpired() {return true;}
	@Override public boolean isAccountNonLocked() {return true;}
	@Override public boolean isCredentialsNonExpired() {return true;}
	@Override public boolean isEnabled() {return true;}

	public MemberEntity getMember() {
		return memberEntity;
	}
}
