package jglib.screen;

import java.awt.Graphics;

/**
 * 更新機能を持たない単純なゲーム画面の作成を円滑に進めていく為に用意された抽象基底クラスです。
 *
 * @author mpp
 */
public abstract non-sealed class SimpleGameScreen extends GameScreenBase {
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.paintGameComponent(g);
  }
}
