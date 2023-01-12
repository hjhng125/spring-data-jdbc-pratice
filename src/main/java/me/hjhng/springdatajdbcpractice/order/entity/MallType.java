package me.hjhng.springdatajdbcpractice.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MallType implements Comparable<MallType> {

  SMART_STORE("스마트 스토어"),
  ELEVEN("11번가"),
  GMARKET("G마켓"),
  AUCTION("옥션"),
  INTERPARK("인터파크"),
  COUPANG("쿠팡"),
  TMON("티켓몬스터"),
  WEMAKEPRICE("위메프"),
  GODO5("고도몰5"),
  ENAMOO("e나무"),
  MAKESHOP("메이크샵"),
  KAKAOTALK_STORE("카카오톡 스토어"),
  KAKAOTALK_PRESENT("카카오톡 선물하기"),
  KAKAO_MAKERS("카카오톡 메이커스"),
  SSG_OPENMARKET("SSG 오픈마켓"),
  SSG_GENERAL("SSG 종합몰"),
  LOTTE_ON("롯데온"),
  CAFE24("카페24"),
  MANUAL("기타쇼핑몰"),
  ;

  private final String mallName;

}
