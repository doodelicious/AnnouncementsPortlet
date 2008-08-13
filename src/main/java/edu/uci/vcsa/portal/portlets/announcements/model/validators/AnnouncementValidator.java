/**
 *  Copyright 2008. The Regents of the University of California. All Rights
 *  Reserved. Permission to use, copy, modify, and distribute any part of this
 *  software including any source code and documentation for educational,
 *  research, and non-profit purposes, without fee, and without a written
 *  agreement is hereby granted, provided that the above copyright notice, this
 *  paragraph and the following three paragraphs appear in all copies of the
 *  software and documentation. Those desiring to incorporate this software into
 *  commercial products or use for commercial purposes should contact Office of
 *  Technology Alliances, University of California, Irvine, 380 University
 *  Tower, Irvine, CA 92607-7700, Phone: (949) 824-7295, FAX: (949) 824-2899. IN
 *  NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
 *  DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING,
 *  WITHOUT LIMITATION, LOST PROFITS, CLAIMS OR DEMANDS, OR BUSINESS
 *  INTERRUPTION, ARISING OUT OF THE USE OF THIS SOFTWARE, EVEN IF THE
 *  UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  THE SOFTWARE PROVIDED HEREIN IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 *  CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 *  ENHANCEMENTS, OR MODIFICATIONS. THE UNIVERSITY OF CALIFORNIA MAKES NO
 *  REPRESENTATIONS AND EXTENDS NO WARRANTIES OF ANY KIND, EITHER IMPLIED OR
 *  EXPRESS, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *  MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE, OR THAT THE USE OF THE
 *  SOFTWARE WILL NOT INFRINGE ANY PATENT, TRADEMARK OR OTHER RIGHTS.
 */
package edu.uci.vcsa.portal.portlets.announcements.model.validators;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.uci.vcsa.portal.portlets.announcements.model.Announcement;

/**
 * @author Erik A. Olsson (eolsson@uci.edu)
 * 
 * $LastChangedBy$
 * $LastChangedDate$
 */
public class AnnouncementValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return Announcement.class.isAssignableFrom(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "addAnn.title.required.error");	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "abstractText", "addAnn.abstract.required.error");	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "addAnn.message.required.error");	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDisplay", "addAnn.start.required.error");	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDisplay", "addAnn.end.required.error");	
		
		Announcement test = (Announcement) obj;
		if (test.getLink() != null && !"".equals(test.getLink().trim())) {
			if (!validUrlFormat(test.getLink()))
				errors.rejectValue("link", "addAnn.link.malformed.error");
		}
		
		Date startDisplay = test.getStartDisplay();
		Date endDisplay = test.getEndDisplay();
		Date now = new Date();
		
		if (startDisplay != null) {
			Calendar calStart = new GregorianCalendar();
			calStart.setTime(startDisplay);
			
			if (calStart.get(Calendar.YEAR) > 2050) {
				errors.rejectValue("startDisplay", "addAnn.toofaraway");
			}

			if (calStart.get(Calendar.YEAR) < 2008) {
				errors.rejectValue("startDisplay", "addAnn.tooold");
			}
		}
		if (endDisplay != null) {
			Calendar calEnd = new GregorianCalendar();
			calEnd.setTime(endDisplay);
			if (calEnd.get(Calendar.YEAR) > 2050) {
				errors.rejectValue("endDisplay", "addAnn.toofaraway");
			}
			if (calEnd.get(Calendar.YEAR) < 2008) {
				errors.rejectValue("endDisplay", "addAnn.tooold");
			}
		}
		if (endDisplay != null && startDisplay != null) {
			Calendar calStart = new GregorianCalendar();
			calStart.setTime(startDisplay);
			Calendar calEnd = new GregorianCalendar();
			calEnd.setTime(endDisplay);
			
			if (endDisplay.before(now) && !endDisplay.equals(startDisplay)) {
				errors.rejectValue("endDisplay", "addAnn.endDisplay.dateinpast");
			}
			if (startDisplay.after(endDisplay)) {
				errors.rejectValue("startDisplay", "addAnn.startDisplay.afterenddisplay");
			}
			if (startDisplay.equals(endDisplay)) {
				errors.rejectValue("endDisplay", "addAnn.endDisplay.sameAs.startDisplay");
			}
		}
		
		
	}

	private boolean validUrlFormat(String link) {
		URL test;
		try {
			test = new URL(link);
		} catch (MalformedURLException e) {
			return false;
		}
		link = test.toString();
		
		if (!link.startsWith("http://") && !link.startsWith("https://")) {
			return false;
		}
		return true;
	}

}
