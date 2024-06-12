/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public abstract class Fish extends GameObject{
    public Fish() {
        isControlledByAi = true;
        isControlledByKeyBoard = false;
        isControlledByMouse = false;
    }
}
