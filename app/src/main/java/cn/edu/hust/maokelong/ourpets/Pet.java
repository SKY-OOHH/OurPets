package cn.edu.hust.maokelong.ourpets;

/**
 * Created by lenovo on 2016/5/14.
 */
public class Pet {
    /**
     * 宠物主题、宠物行为的枚举类
     */
    public enum PetTheme {Beaver,Bear,BlueSky}
    //public enum PetStill {B_eaver,B_ear,B_lueSky}
    public enum PetAction{circle,dance,grin,proud,shy,applaud,confused,grievious,happy,lovely,pitiful,dizzy,envy,hug,logy,sad,snicker,tease,wave}

    /**
     * 宠物主题、宠物行为的实体
     */
    private PetTheme petTheme;
    private PetAction petAction;
    //private PetStill petStill;

    /**
     *宠物主题、宠物行为的get、set方法
     */
    /**
     * 这里暂时默认主题是BlueSky
     * 需要将用户选择的主题传递一个信号到这里，然后用switch语句选择主题
     */
    public PetTheme getPetTheme() {
        petTheme = PetTheme.BlueSky;
        return petTheme;
    }

    public void setPetTheme(PetTheme petTheme) {
        this.petTheme = petTheme;
    }
    /**
     * 获取静态图片源
     * */
    /*public PetStill getPetStatic(PetTheme theme){
        switch (theme){
            case Beaver:
                petStill = PetStill.B_eaver;
                break;
            case Bear:
                petStill = PetStill.B_ear;
                break;
            case BlueSky:
                petStill = PetStill.B_lueSky;
                break;
        }
        return petStill;
    }*/

    public PetAction getPetAction(int random) {
        switch (random) {
            case 0:
                petAction = PetAction.circle;
                break;
            case 1:
                petAction = PetAction.dance;
                break;
            case 2:
                petAction = PetAction.grin;
                break;
            case 3:
                petAction = PetAction.proud;
                break;
            case 4:
                petAction = PetAction.shy;
                break;
            case 5:
                petAction = PetAction.applaud;
                break;
            case 6:
                petAction = PetAction.confused;
                break;
            case 7:
                petAction = PetAction.grievious;
                break;
            case 8:
                petAction = PetAction.happy;
                break;
            case 9:
                petAction = PetAction.lovely;
                break;
            case 10:
                petAction = PetAction.pitiful;
                break;
            case 11:
                petAction = PetAction.dizzy;
                break;
            case 12:
                petAction = PetAction.envy;
                break;
            case 13:
                petAction = PetAction.hug;
                break;
            case 14:
                petAction = PetAction.logy;
                break;
            case 15:
                petAction = PetAction.sad;
                break;
            case 16:
                petAction = PetAction.snicker;
                break;
            case 17:
                petAction = PetAction.tease;
                break;
            case 18:
                petAction = PetAction.wave;
                break;
        }
        return petAction;
    }

    public void setPetAction(PetAction petAction) {
        this.petAction = petAction;
    }

    /**
     * 根据宠物主题、行为获取图片源
     * @return
     */
    public int getActionImageSource(PetTheme theme,PetAction action) {
        int handle = -1;
        switch (theme) {
            case Bear:
                switch(action){
                    case applaud:
                        handle=R.drawable.applaud;
                        break;
                    case confused:
                        handle=R.drawable.confused;
                        break;
                    case grievious:
                        handle=R.drawable.grievious;
                        break;
                    case happy:
                        handle=R.drawable.happy;
                        break;
                    case lovely:
                        handle=R.drawable.lovely;
                        break;
                    default:
                        handle=R.drawable.pitiful;
                        break;
                }
                break;
            case BlueSky:
                switch (action){
                    case dizzy:
                        handle=R.drawable.dizzy;
                        break;
                    case envy:
                        handle=R.drawable.envy;
                        break;
                    case hug:
                        handle=R.drawable.hug;
                        break;
                    case logy:
                        handle=R.drawable.logy;
                        break;
                    case sad:
                        handle=R.drawable.sad;
                        break;
                    case snicker:
                        handle=R.drawable.snicker;
                        break;
                    case tease:
                        handle=R.drawable.tease;
                        break;
                    default:
                        handle=R.drawable.wave;
                        break;
                }
                break;
            case Beaver:
                switch (action){
                    case circle:
                        handle=R.drawable.circle;
                        break;
                    case dance:
                        handle=R.drawable.dance;
                        break;
                    case grin:
                        handle=R.drawable.grin;
                        break;
                    case proud:
                        handle=R.drawable.proud;
                        break;
                    default:
                        handle=R.drawable.shy;
                        break;
                }
                break;
        }
        return handle;
    }
    public int getStillImageSource(PetTheme theme) {
        int handle = -1;
        switch (theme) {
            case Bear:
                handle = R.drawable.bear;
                break;
            case BlueSky:
                handle = R.drawable.bluesky;
                break;
            case Beaver:
                handle = R.drawable.beaver;
                break;
        }
        return handle;
    }
    public int getMoveImageSource(PetTheme theme) {
        //TODO 拖拽特效选择
        int handle = -1;
        switch (theme) {
            case Bear:
                handle = R.drawable.lovely;
                break;
            case BlueSky:
                handle = R.drawable.hug;
                break;
            case Beaver:
                handle = R.drawable.shy;
                break;
        }
        return handle;
    }
}
