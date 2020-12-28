


<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>



<div class="container d-flex justify-content-center mt-100">
    <div class="row">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body text-center"> <span class="myratings"></span>
                    <h4 class="mt-1">Rate us</h4>
                    <fieldset class="rating"> 
                    	<input type="radio" id="star5" name="rating" value="5" />
                    		<label class="full" for="star5" title="Awesome - 5 stars"></label> 
                    	<input type="radio" id="star4half" name="rating" value="4.5" />
                    		<label class="half" for="star4half" title="Pretty good - 4.5 stars"></label>
                    	<input type="radio" id="star4" name="rating" value="4" />
                    		<label class="full" for="star4" title="Pretty good - 4 stars"></label> 
                    	<input type="radio" id="star3half" name="rating" value="3.5" />
                    		<label class="half" for="star3half" title="Meh - 3.5 stars"></label> 
                    	<input type="radio" id="star3" name="rating" value="3" />
                    		<label class="full" for="star3" title="Meh - 3 stars"></label> 
                    	<input type="radio" id="star2half" name="rating" value="2.5" />
                    		<label class="half" for="star2half" title="Kinda bad - 2.5 stars"></label> 
                    	<input type="radio" id="star2" name="rating" value="2" />
                    		<label class="full" for="star2" title="Kinda bad - 2 stars"></label> 
                    	<input type="radio" id="star1half" name="rating" value="1.5" />
                    		<label class="half" for="star1half" title="Meh - 1.5 stars"></label> 
                    	<input type="radio" id="star1" name="rating" value="1" />
                    		<label class="full" for="star1" title="Sucks big time - 1 star"></label> 
                    	<input type="radio" id="starhalf" name="rating" value="0.5" />
                    		<label class="half" for="starhalf" title="Sucks big time - 0.5 stars"></label> 
                    	<input type="radio" class="reset-option" name="rating" value="reset" /> 
                    	</fieldset>
                </div>
            </div>
        </div>
    </div>
</div>