package api.gui.tab;

import java.util.ArrayList;
import java.util.List;

import api.gui.tab.BaseGuiTab.TabSide;
import api.gui.tab.BaseGuiTab.TabState;
import theking530.staticpower.client.gui.BaseGuiContainer;

public class GuiTabManager {
	
	private List<BaseGuiTab> registeredTabs;
	private BaseGuiTab initiallyOpenTab;
	private BaseGuiContainer owningGui;
	
	public GuiTabManager(BaseGuiContainer owner) {
		registeredTabs = new ArrayList<BaseGuiTab>();
		owningGui = owner;
	}	
	public void registerTab(BaseGuiTab tab) {
		registeredTabs.add(tab);
		tab.setManager(this);
	}
	public void removeTab(BaseGuiTab tab) {
		registeredTabs.remove(tab);
	}
	public List<BaseGuiTab> getRegisteredTabs() {
		return registeredTabs;
	}
	public void setInitiallyOpenTab(BaseGuiTab tab) {
		initiallyOpenTab = tab;
		initiallyOpenTab.setTabState(TabState.OPEN);
	}
	public void drawTabs(int tabPositionX, int tabPositionY, int guiWidth, int guiHeight, float partialTicks) {
		List<BaseGuiTab> leftTabs = new ArrayList<BaseGuiTab>();
		List<BaseGuiTab> rightTabs = new ArrayList<BaseGuiTab>();
		
		for(int i=0; i < registeredTabs.size(); i++) {
			if(registeredTabs.get(i).getTabSide() == TabSide.RIGHT) {
				rightTabs.add(registeredTabs.get(i));
			}else{
				leftTabs.add(registeredTabs.get(i));
			}
			
		}
		int maxOffset = owningGui.getGuiTop() + owningGui.getYSize() - (25);
		
		for(int i=rightTabs.size()-1; i >= 0; i--) {
			int offset = 0;
			if(i > 0) {
				for(int j=i-1; j >= 0; j--) {
					offset += (int) rightTabs.get(j).getBounds().getH()-24;
				}
			}
			int adjustedOffset = Math.min(tabPositionY + (i*25) + offset, maxOffset);
			rightTabs.get(i).update(tabPositionX, adjustedOffset, partialTicks);
		}
		
		for(int i=leftTabs.size()-1; i >= 0; i--) {
			int offset = 0;
			if(i > 0) {
				for(int j=i-1; j >= 0; j--) {
					offset += (int) leftTabs.get(j).getBounds().getH()-24;
				}
			}
			int adjustedOffset = Math.min(tabPositionY + (i*25) + offset, maxOffset);
			leftTabs.get(i).update(tabPositionX-guiWidth-21, adjustedOffset, partialTicks);
		}
	}
	public void tabClosed(BaseGuiTab tab) {}
	public void tabOpened(BaseGuiTab tab) {}
	public void tabOpening(BaseGuiTab tab) {
		for(int i=0; i<registeredTabs.size(); i++) {
			if(registeredTabs.get(i) != tab && registeredTabs.get(i).getTabSide() == tab.getTabSide()) {
				registeredTabs.get(i).setTabState(TabState.CLOSED);
			}
		}
	}
	public void tabClosing(BaseGuiTab tab) {}
	public void handleMouseInteraction(int mouseX, int mouseY, int button) {
		for(int i=0; i<registeredTabs.size(); i++) {
			registeredTabs.get(i).mouseInteraction(mouseX, mouseY, button);
		}
	}
	public void handleKeyboardInteraction(char par1, int par2) {
		for(int i=0; i<registeredTabs.size(); i++) {
			registeredTabs.get(i).keyboardInteraction(par1, par2);
		}
	}
	public void handleMouseMoveInteraction(int x, int y) {
		for(int i=0; i<registeredTabs.size(); i++) {
			registeredTabs.get(i).mouseMoveIntraction(x, y);
		}
	}
}
