?-
  G_YY := 0,
  G_Pause := 0,
  G_Player1 := 0,
  G_Player2 := 0,
  G_Racket1 := 100,
  G_Racket2 := 100,
  G_Racket1_Speed := 5,
  G_Racket2_Speed := 5,
  G_Time := 0,
  G_Balls := 0,
  new_ball,
  window(title("Ping-Pong"), size(1000, 550), paint_indirectly).

win_func(paint):-
  text_out("Balls "+G_Balls, pos(20, 20)),
  text_out("Player1="+G_Player1, pos(20, 40)),
  text_out("Player2="+G_Player2, pos(20, 60)),
  line(100, G_Racket1, 100, G_Racket1+150),
  pen(3, rgb(255, 0, 0)),
  line(900, G_Racket2, 900, G_Racket2+150),
  line(100, 500, 900, 500),
  pen(3, rgb(255, 0, 0)),
  (G_Speed_X<0->
    ellipse(100-5, G_YY-5, 100+5, G_YY+5)
  else
    ellipse(900-5, G_YY-5, 900+5, G_YY+5)
  ),
  ellipse(G_X-5, G_Y-5, G_X+5, G_Y+5).

win_func(init) :-
    _ := set_timer(_, 0.01, time_func).

win_func(key_down(38, Repetition)) :- % up
  (G_Racket2_Speed > -2 ->
    G_Racket2_Speed := -2
  else
    G_Racket2_Speed := -10
  ).
win_func(key_down(40, Repetition)) :- % down
  (G_Racket2_Speed < 2 ->
    G_Racket2_Speed := 2
  else
    G_Racket2_Speed := 10
  ).
win_func(key_down(80, Repetition)) :- % P
  G_Pause := 1-G_Pause.


time_func(end) :-
  G_Pause =:= 0,
  G_Time := G_Time+1,
  G_X := G_X+G_Speed_X,
  G_Y := G_Y+G_Speed_Y,
  G_Speed_Y := G_Speed_Y+0.1,
  G_Racket1 := G_Racket1+G_Racket1_Speed,
  G_Racket2 := G_Racket2+G_Racket2_Speed,
  player1,
  (G_Racket1>350, G_Racket1_Speed>0 -> G_Racket1_Speed := 0),
  (G_Racket1<0, G_Racket1_Speed<0 -> G_Racket1_Speed := 0),
  (G_Racket2>350, G_Racket2_Speed>0 -> G_Racket2_Speed := 0),
  (G_Racket2<0, G_Racket2_Speed<0 -> G_Racket2_Speed := 0),
  (G_Y>500 -> G_Speed_Y := -1*G_Speed_Y),
  (G_X<100->
    check_racket(G_Racket1, G_Racket1_Speed, G_Player2)
  ),
  (G_X>900->
    check_racket(G_Racket2, G_Racket2_Speed, G_Player1)
  ),
  update_window(_).

player1:-
  (G_Speed_X<0->
    Time := (100-G_X)/G_Speed_X
  else
    Time := (900-G_X)/G_Speed_X
  ),
  G_YY := G_Y+G_Speed_Y*Time+0.1*Time**2/2,
  (G_YY>350 ->
    G_YY := 350
  ),
  (G_Speed_X<0->
    (G_Racket1+75>G_YY->
      G_Racket1_Speed := 2
    else
      G_Racket1_Speed := -2
    ),
    ((G_Racket1+75-G_YY)/G_Racket1_Speed>Time ->
      G_Racket1_Speed := -1*G_Racket1_Speed
    )
  ).

check_racket(Racket, Racket_Speed, Who):-
  (G_Y>Racket, G_Y<Racket+150 ->
    G_Spin := Racket_Speed,
    G_Speed_X := -1*G_Speed_X
  else
    Who := Who + 1,
    wait(1),
    new_ball
  ).

new_ball:-
  G_Balls := G_Balls+1,
  G_Spin := 0,
  G_X := 750+random(100),
  G_Y := 50+random(100),
  G_Speed_X := -3,
  G_Speed_Y := 3.