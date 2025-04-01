package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {
	
	@Autowired
	ItemRepository itemRepository;
	
	@PostMapping("/items/{id}/delete")
	public String delete(@PathVariable int id) {
		// パスパラメータをもとに削除を実行
		itemRepository.deleteById(id);
		// 画面遷移
		return "redirect:/items";
	}
	
	/**
	 * 商品を更新する
	 * @param categoryId カテゴリID
	 * @param name       商品名
	 * @param price      価格
	 * @return 商品一覧画面のThymeleafテンプレート名
	 */
	@PostMapping("/items/{id}/edit")
	public String update(@PathVariable int id,
						 @RequestParam(defaultValue = "0") Integer categoryId,
						 @RequestParam String name,
						 @RequestParam(defaultValue = "0") Integer price) {
		// リクエストパラメータをもとに更新する商品をインスタンス化
		Item item = new Item(id, categoryId, name, price);
		
		// 商品の更新
		itemRepository.save(item);
		
		// 画面遷移
		return "redirect:/items";
	}
	
	/**
	 * 更新画面表示
	 * @param id    更新対象商品の商品ID
	 * @param model 遷移先画面にデータを引き継ぐスコープ
	 * @return 商品更新画面のThymeleafテンプレート名
	 */
	@GetMapping("/items/{id}/edit")
	public String edit(@PathVariable("id") Integer id,
					   Model model) {
		// パスパラメータをもとにデータベースから商品インスタンスを取得
		Item item = itemRepository.findById(id).get();
		
		// 取得した商品インスタンスをスコープに登録
		model.addAttribute("item", item);
		
		// 画面遷移
		return "editItem";
	}
	
	/**
	 * 商品を新規追加する
	 * @param categoryId カテゴリID
	 * @param name       商品名
	 * @param price      価格
	 * @return 商品一覧画面のThymeleafテンプレート名
	 */
	@PostMapping("/items/add")
	public String store(@RequestParam(defaultValue = "0") Integer categoryId,
						@RequestParam String name,
						@RequestParam(defaultValue = "0") Integer price) {
		// リクエエストパラメータをもとに商品クラスをインスタンス化
		Item item = new Item(categoryId, name, price);
		
		// 商品インスタンスをitemsテーブルに登録
		itemRepository.save(item);
		
		// 画面遷移
		return "redirect:/items";
	}
	
	/**
	 * 商品登録画面表示
	 * @return 商品登録画面のThymeleafテンプレート名
	 */
	@GetMapping("/items/add")
	public String create() {
		// 画面遷移
		return "addItem";
	}
	
	/**
	 * 商品一覧画面表示
	 * @return 商品一覧画面のThymeleafテンプレート名
	 */
	@GetMapping("/items")
	public String index(Model model) {
		
		// カテゴリIDによって処理を分岐
		List<Item> itemList = itemRepository.findAll();
		
		// 取得したカテゴリリストと商品リストをスコープに登録
		model.addAttribute("items", itemList);
		
		// 画面遷移
		return "items";
	}
}
