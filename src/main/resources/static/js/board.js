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
			title: $("#title").val(),
			content: $("#content").val(),
		};

		$.ajax({
			type: "POST",
			url: "/api/boardinsert",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
			
		}).done(function(resp){
			alert("글쓰기 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	}, 
	
}

function boarddelete(id) {
				$.ajax({
					type: "DELETE",
					url: "/api/boarddelete/"+id,
					dataType: "json" 
				}).done(function(resp){
					alert("글 삭제가 완료되었습니다.");
					location.href = "/";
				}).fail(function(error){
					alert(JSON.stringify(error));
				}); 
}

index.init();
















