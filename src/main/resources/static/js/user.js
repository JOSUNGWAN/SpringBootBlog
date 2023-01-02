// Ajax 사용이유
// 1. 요청에 대한 응답을 html이 아닌 Data(Json)로 받기 위
// 2. 비동기통신을 하기 위함 : 일의 순서에 영향받지 않고 실행시키기 위함
let index = {
	init: function(){
		$("#btn-save").on("click", ()=> { // function(){} 이 아닌 ()=>{} this를 바인딩 하기 위함.		
			this.save();
		});
	}, save: function(){
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		// ajax 호출기 default가 비동기호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경해서 insert를 요청!
		$.ajax({
			type: "POST",
			url: "/blog/api/user",
			data: JSON.stringify(data), // http body데이터
			contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json 이라면)=>javascript 오브젝트로 변환
			
		// 회원가입수행 요청
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			console.log(resp);
			location.href = "/blog";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}
}

index.init();