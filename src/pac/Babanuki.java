package pac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Babanuki {
	/************************************************************************
	 * メインプログラム
	 ************************************************************************/
	public static void main(String[] args) throws IOException {
		seiri s = new seiri();
		ranking r = new ranking();
		take_taken t = new take_taken();
		game g = new game();
		
		int[] player_nokori_card;
		String[][] player_card;
		int totalplayer = 0;
		
		System.out.println("\n～～～～～～～～～～～～ババ抜きプログラム～～～～～～～～～～～～\n");
		String print1 = "プレイヤー人数を入力してください>>>";
		do {
			totalplayer = Enter_number.main(print1);
			if(totalplayer == 1) {
				System.out.println("1人でババ抜きするの？（憫笑）お前さみしい奴やな（嗤笑）");
			}
		}while(totalplayer<2);
				
		//カード配布
		System.out.println("\nカードを配布します");
		make_card mc = new make_card(totalplayer);
		player_card = mc.player_card;
		player_nokori_card = mc.player_nokori_card;
		on_display.method(player_card, player_nokori_card, 0);
		
		//カード整理
		System.out.println("\n整理したカード");
		for(int a = 0;a < totalplayer;a++) {
			seiri seiri = s.method(totalplayer, player_card, player_nokori_card, a);
			player_card = seiri.player_c;
			player_nokori_card = seiri.player_n_c;
		}
		on_display.method(player_card,player_nokori_card,0);
		
		//カードを所持しているプレイヤーの表示
		int[] syoji_player = new int[totalplayer];
		for(int i = 0;i < syoji_player.length;i++) {
			syoji_player[i] = i;	//ゲームから抜けたプレイヤーの要素には-1を入れる
		}
		
		//〇抜けを記録する配列
		int[] array_rank = new int[totalplayer];
		for(int i= 0;i<array_rank.length;i++) {
			array_rank[i] = -1;		//初期値は-1
		}
		int rank = 0;
		for(int i = 0;i < totalplayer;i++) {
			ranking ranking = r.method(player_nokori_card, i, syoji_player, array_rank, rank);
			syoji_player = ranking.syoji_player;
			array_rank = ranking.array_rank;
			rank = ranking.rank;
		}
		
		//カードを取るプレイヤーと取られるプレイヤーの決定
		take_taken take_taken = t.method(syoji_player, 0);	//最初はtakeに0を入れる
		int take = take_taken.take;
		int taken = take_taken.taken;
		
		//ゲームをする
		System.out.println("\n-----------------------ゲーム開始！-----------------------");
		game game = g.method(totalplayer,player_card, player_nokori_card,syoji_player, array_rank, rank, take, taken);
		array_rank = game.array_rank;
		rank = game.rank;
		
		//結果発表
		result(array_rank, rank);
	}
	/******************************************************************************
	 * 結果を表示するプログラム
	 ******************************************************************************/
	public static void result(int[] array_rank,int rank) {
		System.out.println("------------------------結果発表------------------------");
		for(int i = 0;i < array_rank.length-1;i++) {
			System.out.println((i+1)+"抜けは player"+(array_rank[i]+1));
		}
		System.out.println("\nババは player"+(array_rank[rank]+1));
		System.out.println("-----------------------また遊んでね-----------------------");
	}
}
class on_display{
	/***************************************************************************
	 * 指定したプレイヤーのカードを表示するプログラム
	 * 
	 * 『パラメーターの最後には、表示したいプレイヤーの番号-1 or 全員表示は-10を入力』
	 ***************************************************************************/
	public static void method(String[][] player_card,int[] player_nokori_card,int num) {
		if(num == -10) {
			for(int i = 0;i < player_card.length;i++) {
				System.out.print("player "+ (i + 1) + "のカード\t『");
				for(int j = 0;j < player_nokori_card[i];j++) {
					if((j + 1) ==  player_nokori_card[i]){
						System.out.println(player_card[i][j] + "』\t");
					}else{
						System.out.print(player_card[i][j]+",");
					}
				}
				System.out.print("残り　→　《" + player_nokori_card[i] + "枚》\n");
			}
		}else {
			if(player_nokori_card[num] == 0) {
				System.out.print("player"+ (num + 1) + "のカードの");
			}else {
				System.out.print("player"+ (num + 1) + "のカード\t『");
			}
			for(int j = 0;j < player_nokori_card[num];j++) {
				if((j + 1) ==  player_nokori_card[num]){
					System.out.println(player_card[num][j] + "』\t");
				}else{
					System.out.print(player_card[num][j]+",");
				}
			}
			System.out.print("残り枚数→　《" + player_nokori_card[num] +"枚》\n");
		}
	}
}
/*****************************************************************************
 * カードを作成するプログラム
 *****************************************************************************/
class make_card{
	int[] player_nokori_card;
	String[][] player_card;
	public make_card(int totalplayer) {
		String[] card = {"Joker",																							//Joker
				"Clu01","Clu02","Clu03","Clu04","Clu05","Clu06","Clu07","Clu08","Clu09","Clu10","Clu11","Clu12","Clu13",	//Clubs　　　
				"Dia01","Dia02","Dia03","Dia04","Dia05","Dia06","Dia07","Dia08","Dia09","Dia10","Dia11","Dia12","Dia13",	//Diamonds
				"Hea01","Hea02","Hea03","Hea04","Hea05","Hea06","Hea07","Hea08","Hea09","Hea10","Hea11","Hea12","Hea13",	//Heart
				"Spa01","Spa02","Spa03","Spa04","Spa05","Spa06","Spa07","Spa08","Spa09","Spa10","Spa11","Spa12","Spa13",	//Spade
		};
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0 ; i < 53 ; i++) {
			list.add(i);
        }		
		Collections.shuffle(list);
		int[] card_num = new int[53];
		for (int i =0;i<card_num.length;i++) {
			card_num[i] = list.get(i);
		}
		int cardamari = 53%totalplayer;
		this.player_nokori_card = new int[totalplayer];
		for (int i = 0;i < player_nokori_card.length;i++) {
			if(i<cardamari) {
				player_nokori_card[i] = 53/totalplayer+1;
			}else {
				player_nokori_card[i] = 53/totalplayer;
			}
		}
		this.player_card = new String[totalplayer][53/totalplayer+3];
		//全ての要素に初期値「XXXno」を入れる
		for(int i = 0;i < player_card.length;i++) {
			for(int j = 0;j < player_card[i].length;j++) {
				this.player_card[i][j] = "XXXno";
			}
		}
		//各要素にカードを入れる
		int cardfn = 0;
		for(int i = 0;i < player_card.length;i++) {
			for(int j = 0;j < player_nokori_card[i];j++) {
				this.player_card[i][j] = card[card_num[cardfn]];
				cardfn++;
			}
		}
	}
}
/******************************************************************************
 * カードを整理するプログラム
 ******************************************************************************/
class seiri{
	int[] player_n_c;
	String[][] player_c;
	/*********************************************************************************
	 * 同じ数字のカードを消去するプログラム
	 *********************************************************************************/
	public seiri method(int totalplayer,String[][] player_num_card, int[] player_num_nokori_card, int num) {
		seiri output = new seiri();
		
		//二次元配列から一次元配列にする
		String[] compare = new String[player_num_card[num].length];
		String[] replace = new String[compare.length];
		//カードの数字だけを残す
		for(int i = 0;i < compare.length;i++) {
			compare[i] = player_num_card[num][i].substring(3,5);
		}
		//同じ数字のカードを見つけて消去する
		for(int i = 0;i < compare.length-1;i++) {
			int j = i + 1;
			for(;j < compare.length;j++) {
				if ((compare[i]).equals(compare[j])) {
					compare[i] = "no";
					compare[j] = "no";
				}
			}
		}
		//カードにマークを付け戻す
		for(int i = 0;i < compare.length;i++) {
			if(compare[i].equals("no")) {
				replace[i] = "XXXno";
			}else {
				replace[i] = player_num_card[num][i].substring(0,3)+compare[i];
			}
		}
		//配列player_num_cardの全ての要素に「XXXno」を入れる
		for(int i = 0;i < player_num_card[num].length;i++) {
			player_num_card[num][i] = "XXXno";
		}
		//配列replaceの要素に「XXXno」が入っている要素を入れない
		int idx0 = 0;
		for(int idx1 = 0;idx1 < player_num_card[num].length;idx1++) {
			if(!(replace[idx1].equals("XXXno"))) {
				player_num_card[num][idx0] = replace[idx1];
				idx0++;
			}
		}
		player_num_nokori_card[num] = idx0;
		output.player_c = player_num_card;
		output.player_n_c = player_num_nokori_card;
		return output;
	}
}
class ranking{
	int[] syoji_player;
	int[] array_rank;
	int rank;
	/**************************************************************************
	 * プレイヤーがゲームから抜けたかを判定するプログラム
	 * 『numはプレイヤー番号を入力』
	 **************************************************************************/
	public ranking method(int[] player_nokori_card, int num,int[] syoji_player,  int[] array_rank, int rank) {
		ranking output = new ranking();
		if(player_nokori_card[num] == 0) {
			System.out.println("▽▲▽▲▽▲▽ プレイヤー"+(num+1)+"が抜けました!! ▽▲▽▲▽▲▽");
			syoji_player[num] = -1;
			array_rank[rank] = num;
			rank++;
		}
		output.syoji_player = syoji_player;
		output.array_rank = array_rank;
		output.rank = rank;
		return output;
	}
}
class take_taken{
	int take = 0;
	int taken = 0;
	/***************************************************************************
	 * カードを取るプレイヤーと取られるプレイヤーを決めるプログラム
	 ***************************************************************************/
	public take_taken method(int[] syoji_player,int take) {
		take_taken output = new take_taken();
		
		String redo ="redo";
		do {
			if(syoji_player[take] != -1) {				//プレイヤーが存在するとき
				redo = null;
			}else if(take == syoji_player.length-1) {	//プレイヤーが存在せずプレイヤー番号が最後のとき
				take = 0;
			}else {										//プレイヤーが存在しないとき
				take++;
			}
		}while(redo == "redo");
		
		taken = syoji_player[take]+1;
		redo = "redo";
		do {
			if(taken >= syoji_player.length) {
				taken = 0;
			}
			if(syoji_player[taken] != -1) {				//プレイヤーが存在するとき
				redo = null;
			}else if(taken == syoji_player.length-1) {	//プレイヤーが存在せずプレイヤー番号が最後のとき
				taken = 0;
			}else {										//プレイヤーが存在しないとき
				taken++;
			}
		}while(redo == "redo");
		
		output.take = syoji_player[take];
		output.taken = syoji_player[taken];
		return output;
	}
}
class game{
	int[] array_rank;
	int rank;
	/******************************************************************************
	 * ゲームをするプログラム
	 ******************************************************************************/
	public game method(int totalplayer,String[][]player_card,int[] player_nokori_card,int[] syoji_player,int[] array_rank,int rank,int take,int taken) throws IOException {
		seiri s = new seiri();
		ranking r = new ranking();
		take_taken t = new take_taken();
		
		game output = new game();
		
		int[] array_take_card = {0,1,2,3,4,5,6,7,8,9,10};
		int take_number = 0;
		int mae = 0;	int ato = 0;			//カードを引いたプレイヤーの残り手札が減ったかを見る
		int count = 1;
		do {
			//System.out.println("カウント　〔"+count+"〕");
			mae = player_nokori_card[take];
			//takeがtakenのカードを引く
			if(take == 0) {
				on_display.method(player_card,player_nokori_card,take);
				//他のプレイヤーのカード数
				for(int i = 1;i < player_nokori_card.length;i++) {
					System.out.println("player"+(i+1)+"の残りカード数《"+player_nokori_card[i]+"枚》 ");
				}	
				String print = ("player"+(taken+1)+"からカードを引く。\n《1～"+player_nokori_card[taken]+"》　の中から数字を選ぶ。>>>");
				do {
					take_number = Enter_number.main(print)-1;
				}while(take_number >= player_nokori_card[taken] || take_number < 0);
				System.out.println("引いたカードは　『"+player_card[taken][take_number]+"』");
			}else {
				do {
					Random r1 = new Random();
					int ran = array_take_card[r1.nextInt(player_nokori_card[taken])];
					take_number = ran;
				}while(take_number >= player_nokori_card[taken] || take_number < 0);
				System.out.println("プレイヤー"+(take+1)+"が、プレイヤー"+(taken+1)+"からカードを引きました。");
			}
			
			//takeにカードを追加する
			player_card[take][player_nokori_card[take]] = player_card[taken][take_number];
			player_nokori_card[take]++;
			seiri seiri = s.method(totalplayer, player_card, player_nokori_card, take);
			player_card = seiri.player_c;
			player_nokori_card = seiri.player_n_c;
			
			ato = player_nokori_card[take];
			
			//プレイヤーがtakenの場合
			if(taken == 0) {
				System.out.println("player"+(take+1)+"がplayer1のカードから『"+player_card[taken][take_number]+"』を引きました。");
			}
			
			//手札が一致したかを確認する
			if(mae>ato) {
				System.out.println("-----------------------カードが一致-----------------------\n");
			}else {
				System.out.println("-----------------------カード不一致-----------------------\n");
			}
			if(take == 0) {
				on_display.method(player_card,player_nokori_card,take);
			}
			//takeが抜けたか判定する
			ranking ranking = r.method(player_nokori_card, take, syoji_player, array_rank, rank);
			syoji_player = ranking.syoji_player;
			array_rank = ranking.array_rank;
			rank = ranking.rank;
						
			//takenのカードから選ばれたtake_numberを削除する
			player_card[taken][take_number] = "XXXno";
			seiri = s.method(totalplayer, player_card, player_nokori_card, taken);
			player_card = seiri.player_c;
			player_nokori_card = seiri.player_n_c;
			
			//takenが抜けたか判定する
			ranking = r.method(player_nokori_card, taken, syoji_player, array_rank, rank);
			syoji_player = ranking.syoji_player;
			array_rank = ranking.array_rank;
			rank = ranking.rank;
			
			//次のtakeとtakenを決める
			take = taken;
			take_taken take_teken = t.method(syoji_player, take);
			take = take_teken.take;
			taken = take_teken.taken;
			
			System.out.println("残りプレイ人数《"+(totalplayer-rank)+"人》");
			count++;
		}while(rank != (totalplayer-1));
		
		for(int i = 0;i<totalplayer;i++) {			//ババをarray_rankに入れる
			if(syoji_player[i] != -1) {
				array_rank[rank] = i;
			}
		}
		System.out.println();
		
		output.array_rank = array_rank;
		output.rank = rank;
		return output;
	}
}
/*****************************************************************************
 * 数字を入力するプログラム
 *****************************************************************************/
class Enter_number{
	public static int main (String print) throws IOException {
		int number = 0;
		String numberS = null;
		String error = null;
		
		System.out.print(print);
		do {
			BufferedReader br =
					new BufferedReader(new InputStreamReader(System.in));
		    numberS = br.readLine();
		    try {
		    	number = Integer.parseInt(numberS);
		    	error = null;
		    }catch(NumberFormatException e) {
		    	System.out.println("正しい値を入力できていません。");
		    	error = "redo";
		    }
		}while(error == "redo");
		return number;
	}
}