package stocksimulation;

public class Userstorage {
	int stockholding;	// 보유 수량, 매도 가능 수량 
	int purchaseamount; // 매수 금액 : 내가 사는데 들어간 총 금액
	int valutionamount; // 평가 금액 : 현재가로 따졌을 때 총 금액
	int breakevenpoint; // 손익분기점
	int stockprice; 	// 현재가
	int valuationprofit;// 평가금액 - 매수금액 = 평가 손익
	int profitrate; 	// 수익률 = [(평가금액 - 매수금액)/매수금액]*100
	String Stocks; 		// 종목
	double [] history;	// 거래내역
	
	
	//매수매도버튼마우스클릭이벤트 발생시
	
}