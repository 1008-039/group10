package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action {

	public abstract void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception;
	//ローカル変数の宣言 1
	//なし
	//リクエストパラメータ―の取得 2
	//なし
	//DBからデータ取得 3
	//なし
	//ビジネスロジック 4
	//なし
	//DBへデータ保存 5
	//なし
	//レスポンス値をセット 6
	//なし
	//JSPへフォワード 7

}
