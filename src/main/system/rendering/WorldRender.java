/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package main.system.rendering;

import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.tiles.Tile;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;


public class WorldRender {
    public Tile[] tileStorage;
    private final MainGame mg;
    public static int[][] worldData;
    public static int[][] worldData1;
    public static int[][] worldData2;
    public Point worldSize = new Point();
    private final int[] trueCollision = new int[5000];


    public WorldRender(MainGame mg) {
        this.mg = mg;
        tileStorage = new Tile[5000];
        getTileImagesNew();
    }


    private void setupTiles(int index, String imagePath, boolean collision) {
        tileStorage[index] = new Tile();
        InputStream is = getClass().getResourceAsStream("/resources/tilesNew/" + imagePath);
        if (is != null) {
            tileStorage[index].tileImage = new Image(is);
            tileStorage[index].collision = collision;
        }
        try {
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(GraphicsContext g2) {
        int worldCol = Math.max(mg.playerX - 21, 0);
        int worldRow = Math.max(mg.playerY - 12, 0);
        int maxCol = Math.min(worldCol + 42, worldSize.x);
        int maxRow = Math.min(worldRow + 25, worldSize.y);
        int worldWidth = worldSize.x * 48;
        Player.screenX = mg.HALF_WIDTH - 24;
        Player.screenY = mg.HALF_HEIGHT - 24;
        int playerX = (int) Player.worldX;
        int playerY = (int) Player.worldY;
        int screenX = Player.screenX;
        int screenY = Player.screenY;
        if (screenX > playerX) {
            Player.screenX = playerX;
            screenX = playerX;
        } else if (playerX + 24 > worldWidth - mg.HALF_WIDTH) {
            Player.screenX = MainGame.SCREEN_WIDTH - (worldWidth - playerX);
            screenX = Player.screenX;
            worldCol = Math.min(Math.max(worldCol - 18, 0), worldSize.y);
        }
        if (screenY > playerY) {
            Player.screenY = playerY;
            screenY = playerY;
        } else if (playerY + 24 > worldWidth - mg.HALF_HEIGHT) {
            Player.screenY = MainGame.SCREEN_HEIGHT - (worldWidth - playerY);
            screenY = Player.screenY;
            worldRow = Math.min(Math.max(worldRow - 10, 0), worldSize.x);
        }
        int num1, num2;
        for (int i = worldCol; i < maxCol; i++) {
            for (int b = worldRow; b < maxRow; b++) {
                num1 = worldData[i][b];
                num2 = worldData1[i][b];
                g2.drawImage(tileStorage[num1].tileImage, i * 48 - playerX + screenX, b * 48 - playerY + screenY);
                if (num2 != -1) {
                    g2.drawImage(tileStorage[num2].tileImage, i * 48 - playerX + screenX, b * 48 - playerY + screenY);
                }
            }
        }
    }

    public void drawSecondLayer(GraphicsContext g2) {
        int worldCol = Math.max(mg.playerX - 21, 0);
        int worldRow = Math.max(mg.playerY - 12, 0);
        int maxCol = Math.min(worldCol + 42, worldSize.x);
        int maxRow = Math.min(worldRow + 24, worldSize.y);
        int num1;
        int worldWidth = worldSize.x * 48;
        int playerX = (int) Player.worldX;
        int playerY = (int) Player.worldY;
        int screenX = Player.screenX;
        int screenY = Player.screenY;
        if (screenX > playerX) {
            Player.screenX = playerX;
            screenX = playerX;
        } else if (playerX + 24 > worldWidth - mg.HALF_WIDTH) {
            Player.screenX = MainGame.SCREEN_WIDTH - (worldWidth - playerX);
            screenX = Player.screenX;
            worldCol = Math.max(worldCol - 18, 0);
        }
        if (screenY > playerY) {
            Player.screenY = playerY;
            screenY = playerY;
        } else if (playerY + 24 > worldWidth - mg.HALF_HEIGHT) {
            Player.screenY = MainGame.SCREEN_HEIGHT - (worldWidth - playerY);
            screenY = Player.screenY;
            worldRow = Math.max(worldRow - 10, 0);
        }
        for (int i = worldCol; i < maxCol; i++) {
            for (int b = worldRow; b < maxRow; b++) {
                num1 = worldData2[i][b];
                if (num1 != -1) {
                    g2.drawImage(tileStorage[num1].tileImage, i * 48 - playerX + screenX, b * 48 - playerY + screenY);
                }
            }
        }
    }

    private void setupCollision(int index) {
        trueCollision[index] = 1;
    }

    private void getTileImagesNew() {
        {
            setupCollision(1093);
            setupCollision(1079);
            setupCollision(1080);
            setupCollision(1081);
            setupCollision(1092);
            setupCollision(1094);
            setupCollision(1105);
            setupCollision(1107);
            for (int i = 0; i < 4; i++) {
                setupCollision(i + 1528);
            }
            for (int j = 704; j <= 886; j += 91) {
                for (int i = 0; i <= 8; i += 4) {
                    setupCollision(i + j);
                }
            }
            setupCollision(3686);
            setupCollision(3685);
            for (int i = 3787; i <= 3792; i++) {
                setupCollision(i);
            }
            for (int i = 3645; i <= 3648; i++) {
                setupCollision(i);
            }
            for (int i = 3653; i <= 3747; i += 13) {
                setupCollision(i);
                setupCollision(i + 1);
                setupCollision(i + 2);
                setupCollision(i + 3);
            }
            for (int i = 3645; i <= 3687; i += 13) {
                setupCollision(i);
                setupCollision(i + 1);
                setupCollision(i + 2);
                setupCollision(i + 3);
            }
            setupCollision(3914);
            for (int i = 4329; i <= 4497; i++) {
                setupCollision(i);
            }
            for (int i = 3445; i <= 3561; i++) {
                setupCollision(i);
            }
            setupCollision(309);
            setupCollision(3975);
            setupCollision(803);
            setupCollision(704);
            setupCollision(311);
            setupCollision(2041);
            setupCollision(2042);
            setupCollision(1937);
            setupCollision(1938);
            setupCollision(2145);
            setupCollision(2146);
            setupCollision(2159);
            setupCollision(4);
            setupCollision(592);
            setupCollision(283);
            setupCollision(2158);
            setupCollision(2267);
            setupCollision(2462);
            setupCollision(2514);
            setupCollision(2228);
            setupCollision(336);
            setupCollision(335);
            setupCollision(322);
            setupCollision(324);
            setupCollision(190);
            setupCollision(5);
            setupCollision(6);
            setupCollision(17);
            setupCollision(18);
            setupCollision(19);
            setupCollision(20);
            setupCollision(30);
            setupCollision(31);
            setupCollision(32);
            setupCollision(53);
            setupCollision(55);
            setupCollision(56);
            setupCollision(57);
            setupCollision(58);
            setupCollision(92);
            setupCollision(93);
            setupCollision(94);
            setupCollision(95);
            setupCollision(96);
            setupCollision(97);
            setupCollision(105);
            setupCollision(106);
            setupCollision(107);
            setupCollision(108);
            setupCollision(109);
            setupCollision(110);
            setupCollision(120);
            setupCollision(121);
            setupCollision(122);
            setupCollision(123);
            setupCollision(134);
            setupCollision(135);
            setupCollision(136);
            setupCollision(147);
            setupCollision(175);
            setupCollision(199);
            setupCollision(200);
            setupCollision(202);
            setupCollision(210);
            setupCollision(211);
            setupCollision(235);
            setupCollision(236);
            setupCollision(266);
            setupCollision(267);
            setupCollision(268);
            setupCollision(269);
            setupCollision(284);
            setupCollision(285);
            setupCollision(292);
            setupCollision(293);
            setupCollision(294);
            setupCollision(295);
            setupCollision(296);
            setupCollision(298);
            setupCollision(305);
            setupCollision(306);
            setupCollision(307);
            setupCollision(308);
            setupCollision(318);
            setupCollision(319);
            setupCollision(320);
            setupCollision(321);
            setupCollision(331);
            setupCollision(332);
            setupCollision(333);
            setupCollision(334);
            setupCollision(572);
            setupCollision(573);
            setupCollision(574);
            setupCollision(575);
            setupCollision(576);
            setupCollision(577);
            setupCollision(578);
            setupCollision(579);
            setupCollision(580);
            setupCollision(581);
            setupCollision(582);
            setupCollision(583);
            setupCollision(584);
            setupCollision(585);
            setupCollision(587);
            setupCollision(588);
            setupCollision(589);
            setupCollision(591);
            setupCollision(592);
            setupCollision(593);
            setupCollision(594);
            setupCollision(595);
            setupCollision(596);
            setupCollision(598);
            setupCollision(599);
            setupCollision(600);
            setupCollision(601);
            setupCollision(602);
            setupCollision(603);
            setupCollision(604);
            setupCollision(605);
            setupCollision(606);
            setupCollision(607);
            setupCollision(608);
            setupCollision(609);
            setupCollision(610);
            setupCollision(611);
            setupCollision(612);
            setupCollision(613);
            setupCollision(614);
            setupCollision(615);
            setupCollision(616);
            setupCollision(617);
            setupCollision(618);
            setupCollision(619);
            setupCollision(620);
            setupCollision(621);
            setupCollision(622);
            setupCollision(623);
            setupCollision(624);
            setupCollision(625);
            setupCollision(626);
            setupCollision(627);
            setupCollision(628);
            setupCollision(629);
            setupCollision(630);
            setupCollision(631);
            setupCollision(632);
            setupCollision(633);
            setupCollision(634);
            setupCollision(635);
            setupCollision(636);
            setupCollision(910);
            setupCollision(911);
            setupCollision(912);
            setupCollision(913);
            setupCollision(914);
            setupCollision(915);
            setupCollision(916);
            setupCollision(917);
            setupCollision(918);
            setupCollision(919);
            setupCollision(920);
            setupCollision(921);
            setupCollision(922);
            setupCollision(923);
            setupCollision(924);
            setupCollision(925);
            setupCollision(926);
            setupCollision(927);
            setupCollision(928);
            setupCollision(929);
            setupCollision(930);
            setupCollision(931);
            setupCollision(932);
            setupCollision(933);
            setupCollision(934);
            setupCollision(935);
            setupCollision(936);
            setupCollision(937);
            setupCollision(938);
            setupCollision(939);
            setupCollision(940);
            setupCollision(941);
            setupCollision(942);
            setupCollision(943);
            setupCollision(944);
            setupCollision(945);
            setupCollision(946);
            setupCollision(947);
            setupCollision(948);
            setupCollision(949);
            setupCollision(950);
            setupCollision(951);
            setupCollision(952);
            setupCollision(953);
            setupCollision(954);
            setupCollision(955);
            setupCollision(956);
            setupCollision(957);
            setupCollision(958);
            setupCollision(959);
            setupCollision(960);
            setupCollision(961);
            setupCollision(962);
            setupCollision(963);
            setupCollision(964);
            setupCollision(965);
            setupCollision(966);
            setupCollision(967);
            setupCollision(968);
            setupCollision(969);
            setupCollision(970);
            setupCollision(971);
            setupCollision(972);
            setupCollision(973);
            setupCollision(974);
            setupCollision(975);
            setupCollision(976);
            setupCollision(977);
            setupCollision(978);
            setupCollision(979);
            setupCollision(980);
            setupCollision(981);
            setupCollision(982);
            setupCollision(983);
            setupCollision(984);
            setupCollision(985);
            setupCollision(986);
            setupCollision(987);
            setupCollision(988);
            setupCollision(989);
            setupCollision(990);
            setupCollision(991);
            setupCollision(992);
            setupCollision(993);
            setupCollision(994);
            setupCollision(995);
            setupCollision(996);
            setupCollision(997);
            setupCollision(998);
            setupCollision(999);
            setupCollision(1_000);
            setupCollision(1_001);
            setupCollision(1_002);
            setupCollision(1_003);
            setupCollision(1_004);
            setupCollision(1_005);
            setupCollision(1_006);
            setupCollision(1_007);
            setupCollision(1_008);
            setupCollision(1_009);
            setupCollision(1_010);
            setupCollision(1_011);
            setupCollision(1_012);
            setupCollision(1_013);
            setupCollision(1_014);
            setupCollision(1_015);
            setupCollision(1_016);
            setupCollision(1_017);
            setupCollision(1_018);
            setupCollision(1_019);
            setupCollision(1_020);
            setupCollision(1_021);
            setupCollision(1_022);
            setupCollision(1_023);
            setupCollision(1_024);
            setupCollision(1_025);
            setupCollision(1_026);
            setupCollision(1_027);
            setupCollision(1_028);
            setupCollision(1_029);
            setupCollision(1_030);
            setupCollision(1_031);
            setupCollision(1_032);
            setupCollision(1_033);
            setupCollision(1_034);
            setupCollision(1_035);
            setupCollision(1_036);
            setupCollision(1_096);
            setupCollision(1_098);
            setupCollision(1_261);
            setupCollision(1_262);
            setupCollision(1_263);
            setupCollision(1_264);
            setupCollision(1_265);
            setupCollision(1_266);
            setupCollision(1_267);
            setupCollision(1_268);
            setupCollision(1_284);
            setupCollision(1_313);
            setupCollision(1_314);
            setupCollision(1_315);
            setupCollision(1_316);
            setupCollision(1_340);
            setupCollision(1_476);
            setupCollision(1_477);
            setupCollision(1_478);
            setupCollision(1_489);
            setupCollision(1_491);
            setupCollision(1_502);
            setupCollision(1_503);
            setupCollision(1_504);
            setupCollision(1547);
            setupCollision(1548);
            setupCollision(1549);
            setupCollision(1550);
            setupCollision(1551);
            setupCollision(1552);
            setupCollision(1553);
            setupCollision(1554);
            setupCollision(1555);
            setupCollision(1556);
            setupCollision(1557);
            setupCollision(1558);
            setupCollision(1559);
            setupCollision(1560);
            setupCollision(1561);
            setupCollision(1562);
            setupCollision(1563);
            setupCollision(1564);
            setupCollision(1565);
            setupCollision(1566);
            setupCollision(1567);
            setupCollision(1568);
            setupCollision(1569);
            setupCollision(1570);
            setupCollision(1571);
            setupCollision(1572);
            setupCollision(1573);
            setupCollision(1574);
            setupCollision(1575);
            setupCollision(1576);
            setupCollision(1577);
            setupCollision(1578);
            setupCollision(1579);
            setupCollision(1580);
            setupCollision(1581);
            setupCollision(1582);
            setupCollision(1583);
            setupCollision(1584);
            setupCollision(1585);
            setupCollision(1599);
            setupCollision(1600);
            setupCollision(1601);
            setupCollision(1602);
            setupCollision(1603);
            setupCollision(1604);
            setupCollision(1605);
            setupCollision(1607);
            setupCollision(1608);
            setupCollision(1609);
            setupCollision(1610);
            setupCollision(1611);
            setupCollision(1612);
            setupCollision(1613);
            setupCollision(1614);
            setupCollision(1615);
            setupCollision(1616);
            setupCollision(1617);
            setupCollision(1618);
            setupCollision(1619);
            setupCollision(1620);
            setupCollision(1621);
            setupCollision(1622);
            setupCollision(1623);
            setupCollision(1624);
            setupCollision(1625);
            setupCollision(1626);
            setupCollision(1627);
            setupCollision(1628);
            setupCollision(1629);
            setupCollision(1630);
            setupCollision(1631);
            setupCollision(1632);
            setupCollision(1633);
            setupCollision(1634);
            setupCollision(1635);
            setupCollision(1636);
            setupCollision(1637);
            setupCollision(1651);
            setupCollision(1652);
            setupCollision(1653);
            setupCollision(1654);
            setupCollision(1655);
            setupCollision(1656);
            setupCollision(1657);
            setupCollision(1658);
            setupCollision(1659);
            setupCollision(1660);
            setupCollision(1661);
            setupCollision(1662);
            setupCollision(1663);
            setupCollision(1664);
            setupCollision(1665);
            setupCollision(1666);
            setupCollision(1667);
            setupCollision(1668);
            setupCollision(1669);
            setupCollision(1670);
            setupCollision(1671);
            setupCollision(1672);
            setupCollision(1673);
            setupCollision(1674);
            setupCollision(1675);
            setupCollision(1676);
            setupCollision(1677);
            setupCollision(1678);
            setupCollision(1679);
            setupCollision(1680);
            setupCollision(1681);
            setupCollision(1682);
            setupCollision(1683);
            setupCollision(1684);
            setupCollision(1685);
            setupCollision(1686);
            setupCollision(1687);
            setupCollision(1688);
            setupCollision(1689);
            setupCollision(1690);
            setupCollision(1691);
            setupCollision(1692);
            setupCollision(1693);
            setupCollision(1694);
            setupCollision(1695);
            setupCollision(1696);
            setupCollision(1697);
            setupCollision(1698);
            setupCollision(1699);
            setupCollision(1700);
            setupCollision(1701);
            setupCollision(1702);
            setupCollision(1703);
            setupCollision(1704);
            setupCollision(1705);
            setupCollision(1706);
            setupCollision(1707);
            setupCollision(1708);
            setupCollision(1709);
            setupCollision(1710);
            setupCollision(1711);
            setupCollision(1712);
            setupCollision(1713);
            setupCollision(1714);
            setupCollision(1715);
            setupCollision(1716);
            setupCollision(1717);
            setupCollision(1718);
            setupCollision(1719);
            setupCollision(1720);
            setupCollision(1721);
            setupCollision(1722);
            setupCollision(1723);
            setupCollision(1724);
            setupCollision(1725);
            setupCollision(1726);
            setupCollision(1727);
            setupCollision(1728);
            setupCollision(1729);
            setupCollision(1730);
            setupCollision(1731);
            setupCollision(1732);
            setupCollision(1733);
            setupCollision(1734);
            setupCollision(1735);
            setupCollision(1736);
            setupCollision(1737);
            setupCollision(1738);
            setupCollision(1739);
            setupCollision(1740);
            setupCollision(1741);
            setupCollision(1742);
            setupCollision(1743);
            setupCollision(1744);
            setupCollision(1745);
            setupCollision(1746);
            setupCollision(1747);
            setupCollision(1748);
            setupCollision(1749);
            setupCollision(1750);
            setupCollision(1751);
            setupCollision(1752);
            setupCollision(1753);
            setupCollision(1754);
            setupCollision(1755);
            setupCollision(1756);
            setupCollision(1757);
            setupCollision(1758);
            setupCollision(1759);
            setupCollision(1760);
            setupCollision(1761);
            setupCollision(1762);
            setupCollision(1763);
            setupCollision(1764);
            setupCollision(1765);
            setupCollision(1766);
            setupCollision(1767);
            setupCollision(1768);
            setupCollision(1769);
            setupCollision(1770);
            setupCollision(1771);
            setupCollision(1772);
            setupCollision(1773);
            setupCollision(1774);
            setupCollision(1775);
            setupCollision(1776);
            setupCollision(1777);
            setupCollision(1778);
            setupCollision(1779);
            setupCollision(1780);
            setupCollision(1781);
            setupCollision(1782);
            setupCollision(1783);
            setupCollision(1784);
            setupCollision(1785);
            setupCollision(1786);
            setupCollision(1787);
            setupCollision(1788);
            setupCollision(1789);
            setupCollision(1790);
            setupCollision(1791);
            setupCollision(1792);
            setupCollision(1793);
            setupCollision(1794);
            setupCollision(1795);
            setupCollision(1796);
            setupCollision(1797);
            setupCollision(1798);
            setupCollision(1799);
            setupCollision(1800);
            setupCollision(1801);
            setupCollision(1802);
            setupCollision(1803);
            setupCollision(1804);
            setupCollision(1805);
            setupCollision(1806);
            setupCollision(1807);
            setupCollision(1808);
            setupCollision(1809);
            setupCollision(1810);
            setupCollision(1811);
            setupCollision(1812);
            setupCollision(1813);
            setupCollision(1814);
            setupCollision(1815);
            setupCollision(1816);
            setupCollision(1817);
            setupCollision(1818);
            setupCollision(1819);
            setupCollision(1820);
            setupCollision(1821);
            setupCollision(1822);
            setupCollision(1823);
            setupCollision(1824);
            setupCollision(1825);
            setupCollision(1826);
            setupCollision(1827);
            setupCollision(1828);
            setupCollision(1829);
            setupCollision(1830);
            setupCollision(1831);
            setupCollision(1832);
            setupCollision(1833);
            setupCollision(1834);
            setupCollision(1835);
            setupCollision(1836);
            setupCollision(1837);
            setupCollision(1838);
            setupCollision(1839);
            setupCollision(1840);
            setupCollision(1841);
            setupCollision(1842);
            setupCollision(1843);
            setupCollision(1844);
            setupCollision(1845);
            setupCollision(1846);
            setupCollision(1847);
            setupCollision(1848);
            setupCollision(1849);
            setupCollision(1850);
            setupCollision(1851);
            setupCollision(1852);
            setupCollision(1853);
            setupCollision(1854);
            setupCollision(1855);
            setupCollision(1856);
            setupCollision(1857);
            setupCollision(1858);
            setupCollision(1859);
            setupCollision(1860);
            setupCollision(1861);
            setupCollision(1862);
            setupCollision(1863);
            setupCollision(1864);
            setupCollision(1865);
            setupCollision(1866);
            setupCollision(1867);
            setupCollision(1868);
            setupCollision(1869);
            setupCollision(1870);
            setupCollision(1871);
            setupCollision(1885);
            setupCollision(1886);
            setupCollision(1887);
            setupCollision(1888);
            setupCollision(1898);
            setupCollision(1899);
            setupCollision(1900);
            setupCollision(1901);
            setupCollision(1950);
            setupCollision(1951);
            setupCollision(1953);
            setupCollision(1954);
            setupCollision(1989);
            setupCollision(1990);
            setupCollision(1991);
            setupCollision(1992);
            setupCollision(2002);
            setupCollision(2003);
            setupCollision(2004);
            setupCollision(2005);
            setupCollision(2054);
            setupCollision(2055);
            setupCollision(2057);
            setupCollision(2058);
            setupCollision(2215);
            setupCollision(2298);
            setupCollision(2337);
            setupCollision(2338);
            setupCollision(2416);
            setupCollision(2425);
            setupCollision(2464);
            setupCollision(2470);
            setupCollision(2471);
            setupCollision(2472);
            setupCollision(2473);
            setupCollision(2474);
            setupCollision(2475);
            setupCollision(2476);
            setupCollision(2477);
            setupCollision(2478);
            setupCollision(2479);
            setupCollision(2480);
            setupCollision(2481);
            setupCollision(2482);
            setupCollision(2483);
            setupCollision(2484);
            setupCollision(2485);
            setupCollision(2486);
            setupCollision(2487);
            setupCollision(2488);
            setupCollision(2489);
            setupCollision(2490);
            setupCollision(2491);
            setupCollision(2492);
            setupCollision(2493);
            setupCollision(2494);
            setupCollision(2495);
            setupCollision(2496);
            setupCollision(2497);
            setupCollision(2498);
            setupCollision(2499);
            setupCollision(2500);
            setupCollision(2501);
            setupCollision(2502);
            setupCollision(2503);
            setupCollision(2504);
            setupCollision(2505);
            setupCollision(2506);
            setupCollision(2507);
            setupCollision(2508);
            setupCollision(2509);
            setupCollision(2510);
            setupCollision(2511);
            setupCollision(2512);
            setupCollision(2513);
            setupCollision(2514);
            setupCollision(2515);
            setupCollision(2516);
            setupCollision(2517);
            setupCollision(2518);
            setupCollision(2519);
            setupCollision(2520);
            setupCollision(2521);
            setupCollision(2522);
            setupCollision(2523);
            setupCollision(2524);
            setupCollision(2525);
            setupCollision(2526);
            setupCollision(2527);
            setupCollision(2528);
            setupCollision(2529);
            setupCollision(2530);
            setupCollision(2531);
            setupCollision(2532);
            setupCollision(2533);
            setupCollision(2534);
            setupCollision(2535);
            setupCollision(2536);
            setupCollision(2537);
            setupCollision(2538);
            setupCollision(2539);
            setupCollision(2540);
            setupCollision(2541);
            setupCollision(2542);
            setupCollision(2543);
            setupCollision(2544);
            setupCollision(2545);
            setupCollision(2546);
            setupCollision(2547);
            setupCollision(2548);
            setupCollision(2549);
            setupCollision(2550);
            setupCollision(2551);
            setupCollision(2552);
            setupCollision(2553);
            setupCollision(2554);
            setupCollision(2555);
            setupCollision(2556);
            setupCollision(2557);
            setupCollision(2558);
            setupCollision(2559);
            setupCollision(2560);
            setupCollision(2561);
            setupCollision(2562);
            setupCollision(2563);
            setupCollision(2564);
            setupCollision(2565);
            setupCollision(2566);
            setupCollision(2567);
            setupCollision(2568);
            setupCollision(2569);
            setupCollision(2570);
            setupCollision(2571);
            setupCollision(2572);
            setupCollision(2573);
            setupCollision(2574);
            setupCollision(2575);
            setupCollision(2576);
            setupCollision(2577);
            setupCollision(2578);
            setupCollision(2579);
            setupCollision(2580);
            setupCollision(2581);
            setupCollision(2582);
            setupCollision(2583);
            setupCollision(2730);
            setupCollision(2731);
            setupCollision(2732);
            setupCollision(2733);
            setupCollision(2744);
            setupCollision(2745);
            setupCollision(2746);
            setupCollision(2756);
            setupCollision(2758);
            setupCollision(2759);
            setupCollision(2800);
            setupCollision(2801);
            setupCollision(2808);
            setupCollision(2811);
            setupCollision(2813);
            setupCollision(2814);
            setupCollision(2825);
            setupCollision(2826);
            setupCollision(2838);
            setupCollision(2840);
            setupCollision(2847);
            setupCollision(2850);
            setupCollision(2851);
            setupCollision(2852);
            setupCollision(2853);
            setupCollision(2940);
            setupCollision(2945);
            setupCollision(2949);
        }
        for (int i = 0; i < 5000; i++) {
            setupTiles(i, i + ".png", trueCollision[i] == 1);
        }
    }
}



