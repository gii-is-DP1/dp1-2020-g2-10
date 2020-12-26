
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>

<div class="wrap">
	<div class="stars">
		<label class="rate">
			<input type="radio" name="radio1" id="star1" value="star1">
			<div class="face"></div>
			<i class="far fa-star star one-star"></i>
		</label>
		<label class="rate">
			<input type="radio" name="radio1" id="star2" value="star2">
			<div class="face"></div>
			<i class="far fa-star star two-star"></i>
		</label>
		<label class="rate">
			<input type="radio" name="radio1" id="star3" value="star3">
			<div class="face"></div>
			<i class="far fa-star star three-star"></i>
		</label>
		<label class="rate">
			<input type="radio" name="radio1" id="star4" value="star4">
			<div class="face"></div>
			<i class="far fa-star star four-star"></i>
		</label>
		<label class="rate">
			<input type="radio" name="radio1" id="star5" value="star5">
			<div class="face"></div>
			<i class="far fa-star star five-star"></i>
		</label>
	</div>
</div>