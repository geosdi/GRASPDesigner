/*
 * FrontlineSMS <http://www.frontlinesms.com>

 * Copyright 2007, 2008 kiwanja
 * 
 * This file is part of FrontlineSMS.
 * 
 * FrontlineSMS is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * 
 * FrontlineSMS is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with FrontlineSMS. If not, see <http://www.gnu.org/licenses/>.
 */
/*
*GRASP(Geo-referential Real-time Acquisition Statistics Platform) Designer Tool <http://www.fabaris.it>
* Copyright © 2012 ,Fabaris s.r.l
* This file is part of GRASP Designer Tool.  
*  GRASP Designer Tool is free software: you can redistribute it and/or modify it
*  under the terms of the GNU Lesser General Public License as published by
*  the Free Software Foundation, either version 3 of the License, or (at
*  your option) any later version.  
*  GRASP Designer Tool is distributed in the hope that it will be useful, but
*  WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser  General Public License for more details.  
*  You should have received a copy of the GNU Lesser General Public License
*  along with GRASP Designer Tool. 
*  If not, see <http://www.gnu.org/licenses/>
*/
package net.frontlinesms.plugins.forms.ui;

import java.awt.GridLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.frontlinesms.FrontlineUtils;
import net.frontlinesms.plugins.forms.data.domain.FormFieldType;
import net.frontlinesms.plugins.forms.ui.components.FComponent;
import net.frontlinesms.plugins.forms.ui.components.PaletteComponent;
import net.frontlinesms.ui.FrontlineUI;
import net.frontlinesms.ui.i18n.InternationalisationUtils;

import org.apache.log4j.Logger;

/**
 * This class represents the Palette.
 * 
 * @author Carlos Eduardo Genz 
 * <li> kadu(at)masabi(dot)com
 * @author Fabaris Srl: Andrea Zanchi,Attila Aknai,Maria Cilione,Rajimol Joseph
 *   www.fabaris.it <http://www.fabaris.it/>  
 */
public class PalettePanel extends JPanel{
	private static Logger LOG = FrontlineUtils.getLogger(PalettePanel.class);
	private static final long serialVersionUID = 1799362551968963234L;
	
	public PalettePanel(DragListener dragListener, DragSource source) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// We have to set the correct font for some languages
		TitledBorder titledBorder = new TitledBorder(InternationalisationUtils.getI18nString(FormsThinletTabController.COMMON_PALETTE));
		titledBorder.setTitleFont(FrontlineUI.currentResourceBundle.getFont());
		
		setBorder(titledBorder);
		for (FormFieldType fieldType : FormFieldType.values()) {
			Class<? extends FComponent> clazz = FComponent.getComponentClass(fieldType);
			FComponent c;
			try {
				c = clazz.newInstance();
				PaletteComponent label = getComponent(c);
				source.createDefaultDragGestureRecognizer(label, DnDConstants.ACTION_COPY, dragListener);
				add(label);
				
			} catch (Exception e) {
				LOG.debug("", e);
			}
		}
		setToolTipText(InternationalisationUtils.getI18nString(FormsThinletTabController.TOOLTIP_DRAG_TO_PREVIEW));
	}

	/**
	 * Returns a palette component using the supplied FComponent instance
	 * to find the icon and the name of the component.
	 * 
	 * @param c
	 * @return
	 */
	private PaletteComponent getComponent(FComponent c) {
		PaletteComponent label = new PaletteComponent(c.getDescription());
		label.setIcon(new ImageIcon(FrontlineUtils.getImage("/icons/components/" + c.getIcon(), getClass())));
		label.setFont(FrontlineUI.currentResourceBundle.getFont());
		label.setComponent(c);
		return label;
	}
	
	/**Public wrapper for getComponent method
	 * 
	 * @param c
	 * @return
	 */
	public PaletteComponent getPaletteFromComponent(FComponent c) {
		return getComponent(c);
	}
}	