<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>실패용 페이지</title>
<style >
	h1{
		color : red;
		font-size :64px;
		text-align : center;
		height : 600px;
		line-height : 600px;
		
		}

</style>

</head>
<body>

	<jsp:include page="../include/header.jsp"/>
	
	<h1>매우 중요하다 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★</h1>
	
	<h2>AJAX! (매우중요함!)</h2>
	
	<pre>
		AJAX == Asynchronous JavaScript And XML
		
		"페이지를 새로고침 하지 않고 서버와 데이터를 주고 받을 수 있는 기술이다"
		"비동기 통신 기술"
		
		우리가 앞에서 개발했던 방식은 동기방식이다
		
		동기방식:
		1. 사용자가 요청을 보낸다
		2. 서버가 요청을 받아서 전체 HTML데이터를 응답
		3. 브라우저는 응답받은 HTML데이터를 처음부터 끝까지 렌더링 -> 전체페이지를 다시 로딩 -> 화면이 한번 깜박 하고 들어온다(SSR)	
	
		비동기방식 : 
		1. 사용자가 요청을 보낸다
		2. JavaScript기술을 이용해서 데이터만 서버로 띡 전송한다
		3. 서버는 JSON / XML 데이터만 응답
		4. JavaScript를 이용해서 필요한 부분만 갱신 -> 부드럽고 빠르다(CSR)
		
		<hr>
		
		<h3>AJAX 장단점</h3>
		
		장점 : 사용자 경험(U.X) 향상 (사용자들이 좋아한다)
			  서버의 부하 감소
			  네트워크 트래픽 절약
			  
		단점 : SEO취약 (검색엔진 노출에 취약하다)		  	
			  브라우저 히스토리 관리 복잡하다
			  JavaScript 의존성이 있다
			  보안 취약점 증가(XSS)
		SPA(Single Page Application) 요즘은 SPA전성시대		  
		React, Vue, Angular => JavaScript/AJAX기반 라이브러리 및 프레임워크	  
	</pre>
	
	<h2>JSON</h2>
	
	<pre>
	Ex) 김예찬(kyc0****)
	
	태준이형 600화 축하축하 => 문자형을 JSON형태로
	
	<reply>
		<name>김예찬</name>
		<maskedId>kyc0****</maskedId>
		<content>태준이형 600화 축하축하</content>
	</reply>
	(구세대 방식)
	
	"reply" :{
		"nickName" : "김예찬"
		"id" : "kyc0****"	
		"content" : "태준이형 600화 축하축하"
	}
	(표준 방식)
	
	JSON == JavaScript Object Notation
	
	사람이 읽기 쉽고, 기계가 파싱하기 쉬운 데이터 교환형식 텍스트기반이라 아주 가볍다
	*진짜로 자바스크립트 객체는 아니다 /  자바스크립트 객체 모양으로 문자열을 만든거다
	
	문법이 아주 엄격하다!
	
	자바스크립트 객체
	{
		name : "김예찬",  				// 키에 따옴표 안적어도된다
		id : "kyc0****"					// 작은 따옴표 사용가능
		content : '태준이형 600화 축하축하'	// 마지막 속성에 컴마 가능
	}
	
	JSON형식
	{
		name : "김예찬",  
		id : "kyc0****",		
		content : "태준이형 600화 축하축하"	
	
	}
	
	장점 :  가독성이 좋다 (XML과 비교해서 훨씬 읽기편하다)
		   데이터 속도 가볍다(XML대비해서 30% 더 가볍다)
		   파싱 속도 빠르다
		   언어 독립적
		   JavaScript 네이티브 지원
		   
	단점 : 주석 불가
		  날짜 타입 없음(문자열로 처리)	 
		  함수 불가능

	웹개발의 표준!! 데이터형식
	REST API의 기본 포맷이다
	설정파일 XML-> JSON(설정파일은 YAML이 인기가 훨씬 많다)		  	   	
	
	</pre>
	
	<pre>
		우리는 AJAX를 이용해서 댓글 기능을 구현해볼 예정
		
		AJAX 사용방법
		
		1. XMLHTTPRequest 객체 생성해서 사용하는 방법 - (요즘은 많이 사용하지 않는다)
		2. jQuery를 사용해서 ajax메소드를 호출하는 방법 + 	
		3. fetch API 활용해서 fetch 호출하는 방법 +
		4. React 배울 때 Axios 라이브러리 설치해서 사용하는 방법 +
	
	</pre>
	
	<h4>jQuery를 이용한 ajax활용</h4>
	
	<h5>요청을 보내고 응답받아오기</h5>
	
	<div class="form-group">
		<div class="form-control">
			<button class="btn btn-sm btn-secondary" onclick="fn1();">요청 보내기!</button>
		</div>
	</div>
	응답 : <label id="output1">아직 응답 없음</label>
	
	<script>
		function fn1(){
			
			// 동기식 요청
			// location.href = '/http://localhost:8089/kh/ajax.do'
			// 비동기식 요청
			$.ajax({
				url : 'http://localhost:8089/kh/ajax1.do',
				type : 'get',
				success : result => {
					console.log(result);
					document.querySelector('#output1').innerHTML = result;
				},
				error : result =>{
					console.log('ajax요청 실패!');
					document.querySelector('#output1').innerHTML = result;
				},
				complete : ()=> {
					console.log('성공실패 무조건')
				}
			});
			
			
			
		}
	</script>
	
	<hr>
	
	<h3>게시글 번호를 보내서 게시글 정보 받아오기</h3>
	
	게시글 번호 : <input type="number" id="boardNo"/> <br>
	
	<button onclick="infoBoard();">게시글 주세요~</button>
	
	<hr>
	
	게시글 제목 : <label id="title">현재 응답 없음</label> <br>
	게시글 내용 : <label id="content">현재 응답 없음</label>
	
	<script >
	
	function infoBoard(){
		$.ajax({
			url : 'http://localhost:8089/kh/ajax2.do',
			type : 'get',
			data : {
				boardNo : document.querySelector('#boardNo').value
			},
			success : result=> {
				console.log(result);
				document.querySelector('#title').innerHTML = result.boardTitle;
				documnet.querySelector('#content').innerHTML = result.boardContent;
			},
			error : e =>{
				console.log(e);
			}
		});			
		
	}
	
	</script>
	
	<hr>
	
	<h3>사진게시글 목록조회</h3>
	
	<!--  <button class="btn btn-lg btn-danger">사진게시글 조회하기</button> -->
	
	<div id="result" style="width: 80%; height : 300px; margin :auto;">
	
	
	</div>
	<button class="btn btn-lg btn-danger" onclick="img();">사진게시글 조회하기</button>
	
	<script>
		function img(){
			$.ajax({
				url : 'http://localhost:8089/kh/ajax3.do',
				type : 'get',
				success : result => {
					console.log(result);
					
				const el = result.map(e =>
					
					`<div>
						<label>글 번호 : \${e.boardNo}</label>
						<div><img src="\${e.src}" width="120" height="70" /></div>
						<p>제목 : \${e.boardTitle}</p>
					</div>`
				).join('');
				 document.querySelector('#result').innerHTML = el;
				}
			});
		}
	
	
	
	</script>
	
	
	
	
	
	
	
	
	 <jsp:include page="../include/footer.jsp"/>
</body>
</html>