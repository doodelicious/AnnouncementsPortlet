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
package edu.uci.vcsa.portal.portlets.announcements.service;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;

import edu.uci.vcsa.portal.portlets.announcements.model.Announcement;
import edu.uci.vcsa.portal.portlets.announcements.model.Topic;
import edu.uci.vcsa.portal.portlets.announcements.model.TopicSubscription;

/**
 * @author Erik A. Olsson (eolsson@uci.edu)
 * 
 * $LastChangedBy$
 * $LastChangedDate$
 */
public interface IAnnouncementService {

	public List<Topic> getAllTopics();

	public Topic getEmergencyTopic();
	
	public void addOrSaveTopic(Topic topic);
	
	public void persistTopic(Topic topic);
	
	public void mergeTopic(Topic topic);
	
	public void addOrSaveAnnouncement(Announcement ann);
	
	public void mergeAnnouncement(Announcement ann);
	
	public Topic getTopic(Long id) throws PortletException;
	
	public Announcement getAnnouncement(Long id) throws PortletException;
	
	public void deleteAnnouncementsPastCurrentTime();
	
	public List<TopicSubscription> getTopicSubscriptionFor(RenderRequest request) throws PortletException;
	
	public void addOrSaveTopicSubscription(List<TopicSubscription> subs);
	
	public void persistTopicSubscription(List<TopicSubscription> subs);
	
	public void deleteTopic(Topic topic);
	
	public void deleteAnnouncement(Announcement ann);
	
}
